import re


def reg(name):
    file = open("json/day.json", "r", encoding="UTF-8")
    txt = file.read()
    txt = txt.split("\n")

    yaml = ""
    flag = False
    for i in range(len(txt)):
        line = txt[i]
        if re.match(r"\s*[{}\[\]][\s\W]*", line):
            if "{" in line:
                flag = True
        else:
            if flag:
                sp = re.findall(r'\s*".+?"', line)
                sp[0] = sp[0][:sp[0].rfind("  ")] + "-" + sp[0][sp[0].rfind("  ") + 1:]
                yaml += ":".join(sp).replace('"', "")[2:]
                if "[" in line:
                    yaml += ":"
                flag = False
            else:
                yaml += ":".join(re.findall(r'\s*".+?"', line)).replace('"', "")[2:]
                if "[" in line:
                    yaml += ":"
            yaml += "\n"

    result = open("result/2_reg_json_to_yaml.yaml", "w", encoding="UTF-8")
    result.write(yaml.replace("[", ""))


if __name__ == "__main__":
    reg("json/day.json")
