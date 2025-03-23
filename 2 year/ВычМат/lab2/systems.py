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


# Метод простых итераций для системы уравнений
def simple_iterations(phi1, phi2, x0, epsilon, max_iterations=1000):
    x = np.array(x0, dtype=float)

    for iteration in range(max_iterations):
        # Вычисление новых значений переменных
        x1 = phi1(x[0], x[1])
        x2 = phi2(x[0], x[1])

        # Проверка сходимости
        if np.abs(x1 - x[0]) < epsilon and np.abs(x2 - x[1]) < epsilon:
            return x1, x2

        # Обновление значений для следующей итерации
        x[0], x[1] = x1, x2

    print("Не удалось найти решение за максимальное количество итераций.")
    return x[0], x[1]


def s1(xy):
    x, y = xy
    return [np.sin(x + y) - (1.5 * x - 0.1), x ** 2 + 2 * y ** 2 - 1]


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
        return -np.sqrt((1 - x ** 2) / 2)  # Второй корень, который может быть правильным для некоторых случаев


# Преобразования для метода простых итераций
def s2_phi1(x, y):
    return pow((3 * y), 1/3)


def s2_phi2(x, y):
    if x < 0:
        return math.sqrt(-x)
    else:
        return 0


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
        # Начальное приближение и точность
        x0 = [0.5, 0.5]
        epsilon = 1e-5

        # Решаем систему уравнений методом простых итераций
        x1, x2 = simple_iterations(s1_phi1, s1_phi2, x0, epsilon)
        print(f"Решение методом простых итераций: x = {x1}, y = {x2}")

        # Решение с использованием fsolve для сравнения
        solution1 = fsolve(s1, x0)
        print("Первое решение fsolve:", solution1)

        # Второй набор начальных приближений
        x0 = [-1, 0]
        x1, x2 = simple_iterations(s1_phi1, s1_phi2, x0, epsilon)
        print(f"Решение методом простых итераций для (-0.5, -0.5): x = {x1}, y = {x2}")
    else:
        plot_system(s2)
        # Начальное приближение и точность
        x0 = [0.5, 0.5]
        epsilon = 1e-5

        # Решаем систему уравнений методом простых итераций
        x1, x2 = simple_iterations(s2_phi1, s2_phi2, x0, epsilon)
        print(f"Решение методом простых итераций: x = {x1}, y = {x2}")

        # Решение с использованием fsolve для сравнения
        solution1 = fsolve(s2, x0)
        print("Первое решение fsolve:", solution1)

        # Второй набор начальных приближений
        x0 = [-1, 0]
        x1, x2 = simple_iterations(s2_phi1, s2_phi2, x0, epsilon)
        print(f"Решение методом простых итераций для (-0.5, -0.5): x = {x1}, y = {x2}")
