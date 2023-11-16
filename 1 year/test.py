class Convertor:
    def __init__(self):
        self.file = None
        self.txt = None

    def get_word(self, word):
        return word[word.find("\"") + 1: word.rfind("\"")]

    def items(self, txt):
        n = 0  # счетчик '"'
        txt_copy = txt
        for k in range(len(txt)):
            if txt[k] == "\"":
                n += 1
            if txt[k] == ":" and n == 2:
                key_word = txt_copy[:k]
                key_word = self.get_word(key_word)

                arg_word = txt_copy[k + 1:]
                if "\"" not in arg_word:
                    arg_word = arg_word.replace(" ", "")
                    arg_word = arg_word.replace(",", "")
                else:
                    arg_word = self.get_word(arg_word)

                if arg_word[-1] == "{" or arg_word[-1] == "[":
                    arg_word = ""
                if ":" in arg_word:
                    arg_word = "'" + arg_word + "'"

                if arg_word == "null":
                    arg_word = ""
                return [key_word, arg_word]
        return [txt]

    def convert(self, path):
        self.file = open(path, "r", encoding="UTF-8")
        self.txt = self.file.read().split("\n")

        yaml = "---\n"
        tab = 0
        link = ""  # переменная для отслежиавния вложенности
        for i in range(len(self.txt)):
            stroka = self.txt[i]
            key_and_word = self.items(stroka)

            if len(key_and_word) == 2:
                # в строке есть ключ и значение
                key, word = self.items(stroka)
                if len(link) >= 2 and (link[-1] == "[" or (link[-1] == "{" and link[-2] == "}") or
                                       (link[-1] == "{" and link[-2] == "[")):
                    # если начинается новый элемент
                    yaml += " " * (tab - 2) + "- " + key + ": " + word + "\n"
                    link += "."
                else:
                    # не новый элемент
                    yaml += " " * tab + key + ": " + word + "\n"

                if "{" in stroka:
                    tab += 2
            else:
                # если есть только ключ или значение
                if ("{" not in stroka) and ("[" not in stroka) and ("}" not in stroka) and ("]" not in stroka):
                    elem = stroka.replace("\"", "").replace(" ", "").replace(",", "")
                    # спец символы
                    if elem == "null": elem = ""
                    yaml += " " * (tab - 2) + "- " + elem + "\n"

            for x in ['[', ']', '{', '}']:
                if x in stroka and x == "[":
                    tab += 2
                elif x in stroka and x == "]":
                    tab -= 2
                if x in stroka:
                    link += x

            if "{}" == link[-2:]:
                tab -= 2
        return yaml


if __name__ == "__main__":
    con = Convertor()
    result = open("result/proverka.yaml", "w", encoding="UTF-8")
    result.write(con.parse("json/days.json"))
