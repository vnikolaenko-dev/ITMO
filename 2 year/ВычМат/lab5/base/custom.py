from math import sin, cos

from solve import solve


def custom():
    chose = int(input("Выберете функцию: \n"
                      "1) sin(x)\n"
                      "2) cos(x)\n"
                      "3) x ** 2\n"))
    l = int(input("Введите левую границу: "))
    r = int(input("Введите правую границу: "))
    n = int(input("Введите количество точек: "))

    h = (r - l) / n

    xs = []
    ys = []
    for i in range(n):
        xs.append(l + h * i)
        if chose == 1: ys.append(sin(l + h * i))
        if chose == 2: ys.append(cos(l + h * i))
        if chose == 3: ys.append((l + h * i) ** 2)
    solve(int(input("Введите точку x: ")), xs, ys)
