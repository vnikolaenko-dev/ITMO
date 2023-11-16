import json
import yaml


def lib(name):
    with open(name, "r", encoding="UTF-8") as js:
        data = json.load(js)

    with open('result/1_lib_to_yaml.yaml', 'w', encoding="UTF-8") as yml:
        yaml.dump(data, yml, allow_unicode=True, encoding="UTF-8")

    with open('result/1_lib_to_yaml.yaml', 'w', encoding="UTF-8") as yml:
        yaml.safe_dump(data, yml, allow_unicode=True, encoding="UTF-8")


if __name__ == "__main__":
    lib("json/day.json")
