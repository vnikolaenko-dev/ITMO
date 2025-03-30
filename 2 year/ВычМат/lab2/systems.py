import math

import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import fsolve


# Функция для построения графика системы уравнений
def plot_system(system):
    # Генерация точек на сетке
    x = np.linspace(-2, 2, 400)
    y = np.linspace(-2, 2, 400)
    X, Y = np.meshgrid(x, y)

    # Вычисление значений для обоих уравнений на сетке
    Z1 = np.array([system([x_, y_])[0] for x_, y_ in zip(np.ravel(X), np.ravel(Y))]).reshape(X.shape)
    Z2 = np.array([system([x_, y_])[1] for x_, y_ in zip(np.ravel(X), np.ravel(Y))]).reshape(X.shape)

    # Построение графиков: красный для первого уравнения, синий для второго
    plt.contour(X, Y, Z1, levels=[0], colors='r')
    plt.contour(X, Y, Z2, levels=[0], colors='b')
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('График системы уравнений')

    plt.show()


def check_convergence(phi1, phi2, x0, y0):
    try:
        diff1_x = abs((phi1(x0 + 1e-5, y0) - phi1(x0, y0)) / 1e-5)
        diff1_y = abs((phi1(x0, y0 + 1e-5) - phi1(x0, y0)) / 1e-5)
        diff2_x = abs((phi2(x0 + 1e-5, y0) - phi2(x0, y0)) / 1e-5)
        diff2_y = abs((phi2(x0, y0 + 1e-5) - phi2(x0, y0)) / 1e-5)
        q = max(diff1_x + diff1_y, diff2_x + diff2_y)
        print("q =", q)
        return q < 1
    except:
        return False


# Метод простых итераций для системы уравнений
def simple_iterations(f, phi1, phi2, x0, epsilon, max_iterations=1000):
    x = np.array(x0, dtype=float)
    n = 0

    if not check_convergence(phi1, phi2, x[0], x[1]) or check_convergence(phi1, phi2, x[0], x[1]) is None:
        print("Метод может не сойтись")

    for iteration in range(max_iterations):
        n += 1
        # Вычисление новых значений переменных
        x1 = phi1(x[0], x[1])
        x2 = phi2(x[0], x[1])

        if x1 is None or x2 is None:
            print("Возникла ошибка при вычислении, корень не может быть найден")
            return [None, None]

        print(n, x1, x2, x1 - x[0], x2 - x[1])
        # Проверка окончания вычислений
        max_dif = max(abs(x1 - x[0]), abs(x2 - x[1]))
        if max_dif < epsilon and (abs(f([x1, x2])[0]) < epsilon and abs(f([x1, x2])[1]) < epsilon):
            print("Результат: ", f([x[0], x2])[0], f([x1, x2])[1])
            return x1, x2

        # Обновление значений для следующей итерации
        x[0], x[1] = x1, x2

    print("Результат: ", f(x)[0], f(x)[1])
    return x[0], x[1]


def s1(xy):
    x, y = xy
    return [np.sin(y) - 1.5 * x + 0.1, x ** 2 + 2 * y ** 2 - 1]


def s2(xy):
    x, y = xy
    return [x ** 3 - 3 * y, y ** 2 + x]


# Преобразования для метода простых итераций
def s1_phi1(x, y):
    # Из первого уравнения x = f1(x, y) -> x = (sin(y) + 0.1) / 1.5
    return (np.sin(y) + 0.1) / 1.5


def s1_phi2(x, y):
    # Из второго уравнения y = f2(x, y) -> y = sqrt((1 - x^2) / 2)
    if (1 - x ** 2) >= 0:  # Проверка на допустимость значения под корнем
        return np.sqrt((1 - x ** 2) / 2)
    else:
        return None  # Второй корень, который может быть правильным для некоторых случаев


# Преобразования для метода простых итераций
def s2_phi1(x, y):
    return pow((3 * y), 1 / 3)


def s2_phi2(x, y):
    if x <= 0:
        return math.sqrt(-x)
    else:
        return None


def run():
    print("Выберите систему:")
    print("    | sin(y) = 1.5x - 0.1")
    print("1) {")
    print("    | x^2 + 2y^2 = 1")
    print("------------------------------")
    print("    | x^3 - 3y = 0")
    print("2) {")
    print("    | y^2 = -x")
    print()

    u = input()
    # Строим график системы уравнений
    if u == "1":
        plot_system(s1)
        # Второй набор начальных приближений
        x0 = [float(input("Введите координату точки приближения по X: ")),
              float(input("Введите координату точки приближения по Y: "))]
        x1, x2 = simple_iterations(s1, s1_phi1, s1_phi2, x0, float(input("Введите допустимую погрешность: ")))
        print(f"Решение методом простых итераций: x = {x1}, y = {x2}")
    else:
        plot_system(s2)
        # Второй набор начальных приближений
        x0 = [float(input("Введите координату точки приближения по X: ")),
              float(input("Введите координату точки приближения по Y: "))]
        x1, x2 = simple_iterations(s2, s2_phi1, s2_phi2, x0, float(input("Введите допустимую погрешность: ")))
        print(f"Решение методом простых итераций: x = {x1}, y = {x2}")
