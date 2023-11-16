# 4
import re


def test(text, n):
    res = re.findall(r'(?:\b[aA]|[d-zD-Z]{1,}[aA])[d-zD-Z]{2}[bB][d-zD-Z]{2}[cC][d-zD-Z]*(?: ){0,1}', text)
    print("ТЕСТ №" + str(n) + "\nИсходня строка: '" + text + "'")
    print("Подходит под шаблон:\n" + " ".join(res))
    print()


test("AbC AfBfC AffBffC", 1)
test("fffAffBffCfff fffaffbffcff", 2)
test("fffAffBffCfff fffAffBffCfff", 3)
test("11a11b11c11 +a++b++c+", 4)
test("AvvBnnC", 5)
