# 409244
# 2 - X
# 0 - -
# 3 - |

import re


def test(text, n):
    print("ТЕСТ №" + str(n) + "\nИсходня строка: '" + text + "'")
    print(len(re.findall(r"X-\|\s*", text)))
    print()


test("XXX-|", 1)
test("XXX-| ### X-| ###", 2)
test("ИваX-|н", 3)
test("X-| X-| X-| X-| 0-)", 4)
test("XX-) X-|", 5)
