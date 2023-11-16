def no_reg(name):
    file = open(name, "r", encoding="UTF-8")
    txt = file.read()
    txt = txt.split("\n")

    yaml = ""
    flag = False
    for i in range(len(txt)):
        line = txt[i]
        if line.replace(" ", "") in ["{", "}", "},", "[", "]", "],"]:
            if "{" in line:
                flag = True
        else:
            if flag:
                line = line[:line.find('"')][:-2] + "- " + line[line.find('"'):]
                yaml += line.replace('",', "").replace('"', "")[2:]
                flag = False
            else:
                yaml += line.replace('",', "").replace('"', "")[2:]
            yaml += "\n"

    result = open("result/0_rep_json_yaml.yaml", "w", encoding="UTF-8")
    result.write(yaml.replace("{", "").replace("[", "").replace("}", "").replace("]", ""))


if __name__ == "__main__":
    no_reg("json/day.json")
