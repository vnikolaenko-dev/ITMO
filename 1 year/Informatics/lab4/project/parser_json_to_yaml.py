import re
import pprint


class Convertor:
    def __init__(self):
        self.n = 0
        self.yaml = ""
        self.md = ""

    def clear_el(self, i):
        if '"' in i:
            i = i[i.find('"') + 1:i.rfind('"')]
            return i
        else:
            i = i.replace(" ", "").replace("\n", "")
        if '"' == i[0] or ":" in i:
            if ',' in i:
                return i[1: i.rfind(",") - 1]
            else:
                return i[1:-1]
        else:
            if i[0].isdigit():
                if ',' in i:
                    return int(i.replace(",", ""))
                else:
                    return int(i)
            elif i[:-1] == "null" or i == "null":
                return None
            elif i[:-1] == "true" or i == "true":
                return True
            elif i[:-1] == "false" or i == "false":
                return False

    def parse(self, txt, construction, new=False):
        if new: self.n = 0
        i = txt[self.n]

        if len(txt) == 1:
            return self.clear_el(txt[0])

        while re.fullmatch("\s*\n", i):
            self.n += 1
            i = txt[self.n]

        # Список
        if re.findall('"[\w\d]+": \[', i):
            key = re.findall('"[\w\d]+"', i)[0].replace('"', "")
            self.n += 1
            if type(construction) is list:
                construction.append(self.parse(txt, []))
            else:
                construction[key] = self.parse(txt, [])

        elif i.replace(" ", "").replace("\n", "") == "[":
            if construction is None:
                self.n += 1
                construction = self.parse(txt, [])

        # Ключ + Элемент
        elif re.findall('"[\w\d]+": {', i):
            key = re.findall('"[\w\d]+"', i)[0].replace('"', "")
            self.n += 1
            if type(construction) is list:
                sp = {}
                sp[key] = self.parse(txt, {})
                construction.append(sp)
            else:
                construction[key] = self.parse(txt, {})

        # Элемент
        elif i.replace(" ", "").replace("\n", "") == "{":
            self.n += 1
            if type(construction) is list:
                construction.append(self.parse(txt, {}))
            else:
                return self.parse(txt, {})

        # Ключ - Значение
        elif re.findall('"[\w\d]+": "[\w\d\W\s]+"', i) or re.findall('"[\w\d]+": [\w\d]+', i):
            key = self.clear_el(re.findall('"[\w\d]+"', i)[0])
            i.replace(key, "")
            if len(re.findall('"[\w\d]+": "[\w\d\W\s]+"', i)) == 1:
                value = re.findall('"[\w\d\W\s]+"', i[i.find(":"):])[0]
            else:
                value = re.findall('[\w\d]+', i[i.find(":"):])[0]

            value = self.clear_el(value)
            if type(construction) is list:
                sp = {}[key] = value
                construction.append(sp)
            else:
                construction[key] = value

        # Значение
        elif re.findall('[\w\d\W\s]+', i):
            if re.findall('[}\]]', i):
                return construction

            for x in i:
                if x in ["{", "[", "]", "}"]:
                    break
            else:
                construction.append(self.clear_el(i))

        # Выход из рекурсии
        if len(txt) == self.n + 1:
            self.n = 0
            return construction

        # Конец конструкции
        if re.findall('[}\]]', i):
            return construction

        self.n += 1
        return self.parse(txt, construction)

    def to_yaml(self, dictionary, tab=0, flag=False, new=False):
        if new:
            if type(dictionary) is dict:
                if len(dictionary.keys()) == 0:
                    return "{}"
            if type(dictionary) is list:
                if len(dictionary) == 0:
                    return "[]"
            elif type(dictionary) is bool or type(dictionary) is str or type(dictionary) is int:
                return str(dictionary)
            self.n = 0
            self.yaml = ""

        if type(dictionary) is dict:
            for i in dictionary.keys():
                if type(dictionary[i]) is not list and type(dictionary[i]) is not dict:
                    if flag:
                        self.yaml += " " * (tab - 2) + "- " + i + ": " + str(dictionary[i]) + "\n"
                        flag = False
                    else:
                        self.yaml += " " * tab + i + ": " + str(dictionary[i]) + "\n"
                else:
                    if flag:
                        self.yaml += " " * (tab - 2) + "- " + i + ":\n"
                        flag = False
                    else:
                        self.yaml += " " * tab + i + ":\n"
                    tab += 2
                    self.to_yaml(dictionary[i], tab)
                    tab -= 2
        else:
            if type(dictionary) is list:
                tab += 2
                for i in dictionary:
                    self.to_yaml(i, tab, True)
                tab -= 2
            else:
                self.yaml += " " * (tab - 2) + "- " + str(dictionary) + "\n"
        return self.yaml

    def to_md(self, dictionary, tab=0, flag=False, new=False):
        if new:
            if type(dictionary) is dict:
                if len(dictionary.keys()) == 0:
                    return "{}"
            if type(dictionary) is list:
                if len(dictionary) == 0:
                    return "[]"
            elif type(dictionary) is bool or type(dictionary) is str or type(dictionary) is int:
                return str(dictionary)
            self.n = 0
            self.md = "~~~\n"

        if type(dictionary) is dict:
            for i in dictionary.keys():
                if type(dictionary[i]) is not list and type(dictionary[i]) is not dict:
                    if flag:
                        self.md += "&nbsp;" * (tab - 2) + i + ": " + str(dictionary[i]) + "\n"
                        flag = False
                    else:
                        self.md += "&nbsp;" * tab + i + ": " + str(dictionary[i]) + "\n"
                else:
                    if flag:
                        self.md += "&nbsp;" * (tab - 2) + i + ":\n"
                        flag = False
                    else:
                        self.md += "&nbsp;" * tab + i + ":\n"
                    tab += 2
                    self.to_md(dictionary[i], tab)
                    tab -= 2
        else:
            if type(dictionary) is list:
                tab += 2
                for i in dictionary:
                    self.to_md(i, tab, True)
                tab -= 2
            else:
                self.md += "&nbsp;" * (tab - 2) + str(dictionary) + "\n"
        return self.md


if __name__ == "__main__":
    data = open("json/days.json", "r", encoding="UTF-8").readlines()
    convertor = Convertor()
    pp = pprint.PrettyPrinter(indent=2)

    dictionary = convertor.parse(data, None)
    with open('result/3_parser_json_to_yaml.yaml', 'w', encoding="UTF-8") as file:
        file.write(convertor.to_yaml(dictionary, 0, False, True))

    with open('result/parser_json_to_md.md', 'w', encoding="UTF-8") as file:
        file.write(convertor.to_md(dictionary, 0, False, True))
