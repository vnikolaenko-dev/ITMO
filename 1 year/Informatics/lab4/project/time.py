from rep_json_to_yaml import no_reg
from lib_json_to_yaml import lib
from reg_json_to_yaml import reg
from parser_json_to_yaml import Convertor

import time
import json
import yaml
import os
import re

start_time = time.time()
for i in range(1000):
    no_reg('json/day.json')
end_time = time.time()
print("Время без регулярых выражений (1000) -", end_time-start_time)

start_time = time.time()
for i in range(1000):
    lib('json/day.json')
end_time = time.time()
print("Время для библиотеки (1000) -", end_time-start_time)

start_time = time.time()
for i in range(1000):
    reg('json/day.json')
end_time = time.time()
print("Время с регулярными выражениями (1000) -", end_time-start_time)

con = Convertor()
start_time = time.time()
for i in range(1000):
    data = open('json/day.json', "r", encoding="UTF-8").readlines()
    data = con.parse(data, None)
    with open('result/time.yaml', 'w', encoding="UTF-8") as file:
        file.write(con.to_yaml(data, 0, False, True))
end_time = time.time()
print("Время для моего парсера (1000) -", end_time-start_time)

os.remove('result/time.yaml')
