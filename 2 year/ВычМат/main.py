import math
from math import cos, sin


def f1(x, y):
    return 1.5 * x + y ** 2 + 1.5 * y


def f2(x, y):
    return sin(x) + cos(y)


def f3(x, y):
    return sin(x) - y


import numpy as np
import matplotlib.pyplot as plt

err = 10 ** 100


def eiler(func, y, x, xn, h, e, n, points=None):
    global err
    if points is None:
        points = ([x], [y])  # Инициализация списков для хранения x и y

    # Вычисление с шагом h

    yn = y + h * func(x, y)
    # Проверка на бесконечность или слишком большое значение
    if math.isinf(yn) or abs(yn) > 1e10:
        print(f"Решение достигло особенности на x = {x + h:.5f}, y = {yn:.10f}")
        err = -1
        return

    # Вычисление с шагом h/2 (два шага по h/2)
    y_half = y + (h / 2) * func(x, y)
    y_half = y_half + (h / 2) * func(x + h / 2, y_half)

    # Оценка погрешности по правилу Рунге
    error = abs((yn - y_half) / (2 ** 2 - 1))
    err = error

    # Вывод результатов
    print(f"{n}) x = {x:.5f}, y_{n} = {yn:.10f}, Погрешность (Рунге) = {error:.10f}")

    # Условие завершения
    if x >= xn:
        return points

    # Сохранение текущей точки
    points[0].append(x + h)
    points[1].append(yn)

    # Рекурсивный вызов
    return eiler(func, yn, x + h, xn, h, e, n + 1, points)


def mod_eiler(func, y, x, xn, h, e, n, points=None):
    global err
    if points is None:
        points = ([x], [y])  # Инициализация списков для хранения x и y

    # Вычисление с шагом h
    yn = y + h * func(x + h / 2, y + h / 2 * func(x, y))

    # Вычисление с шагом h/2 (два шага по h/2)
    y_half = y + (h / 2) * func(x, y)
    y_half = y_half + (h / 2) * func(x + h / 2, y_half)

    # Оценка погрешности по правилу Рунге
    error = abs((yn - y_half) / (2 ** 4 - 1))
    err = error

    # Проверка на бесконечность или слишком большое значение
    if math.isinf(yn) or abs(yn) > 1e10:
        print(f"Решение достигло особенности на x = {x + h:.5f}, y = {yn:.10f}")
        err = -1
        return

    # Вывод результатов
    print(f"{n}) x = {x:.5f}, y_{n} = {yn:.10f}, Погрешность (Рунге) = {error:.10f}")

    # Условие завершения
    if x >= xn:
        return points

    # Сохранение текущей точки
    points[0].append(x + h)
    points[1].append(yn)

    # Рекурсивный вызов
    return mod_eiler(func, yn, x + h, xn, h, e, n + 1, points)


def rk4_start(func, y0, x0, h, n_points, show=False):
    """Метод Рунге-Кутты 4-го порядка для начальных точек."""
    x = [x0]
    y = [y0]
    if show: print("4 элемента - Рунге Кута")
    for i in range(n_points):
        x_curr = x[-1]
        y_curr = y[-1]

        if show: print(f"{i + 1}) \tx = {x_curr:.5f}, y_h = {y_curr:.5f}")

        k1 = func(x_curr, y_curr)
        k2 = func(x_curr + h / 2, y_curr + h / 2 * k1)
        k3 = func(x_curr + h / 2, y_curr + h / 2 * k2)
        k4 = func(x_curr + h, y_curr + h * k3)
        y_next = y_curr + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4)

        x.append(x_curr + h)
        y.append(y_next)

    if show: print(f"{4}) \tx = {x[-1]:.5f}, y_h = {y[-1]:.5f}")
    return x, y


def calculate_y(x, x0, y0):
    term1 = (math.exp(x - x0) * (2 * y0 + math.sin(x0) + math.cos(x0))) / 2
    term2 = math.sin(x) / 2
    term3 = math.cos(x) / 2
    return term1 + term2 + term3

def milne(f, y_old, x, xn, h, e, n, h_for_runge, points=None):
    global err
    if points is None:
        # Инициализация начальными точками (4 точки с помощью РК4)
        x_init, y_init = rk4_start(f, y_old, x, h_for_runge, 3, True)
        print("--------------------------")
        points = (x_init, y_init)

    # Текущие значения
    xi = points[0]
    y = points[1]

    for i in range(4, int((xn - x) / h_for_runge) + 1):
        h = h_for_runge
        if xi[-1] + h > xn: break
        xi.append(xi[-1] + h)
        yp = y[i - 4] + 4 * h * (2 * f(xi[i - 3], y[i - 3]) - f(xi[i - 2], y[i - 2]) + 2 * f(xi[i - 1], y[i - 1])) / 3
        while True:
            yc = y[i - 2] + h * (f(xi[i - 2], y[i - 2]) + 4 * f(xi[i - 1], y[i - 1]) + f(xi[i], yp)) / 3
            yp = yc
            if abs(yc - yp) < e and xi[i] in can_x:
                print(f"{can_x.index(xi[i]) + 1} x={xi[i]} y={yp} y_correct={calculate_y(xi[0], xi[i], y_old)}")
                break
            elif abs(yc - yp) < e:
                break
        y.append(yp)

    x_exact, y_exact = compute_exact_solution_for_graf(f, y[0], xi[0], xn, h / 100)
    if y_exact[-1] - y[-1] < e and h > 0.5:
        return milne(f, y_old, x, xn, h, e, n, h_for_runge / 2)

    err = e
    return (xi, y)

can_x = []
def solve(func, y, x, xn, h, e, n):
    global err
    print("--------------------------------------\nМетод Эйлера")
    e_h = h

    while True:
        eiler_points = eiler(func, y, x, xn, e_h, e, n)
        if (err <= e and err > 0): break
        if (err < 0):
            print("Плохие данные")
            break
        print(f"Шаг {e_h} оказался недостаточным для заданной точности, уменьшаю шаг {e_h / 2}")
        e_h = e_h / 2
        if e_h == 0:
            break
    print(f"Удовлетворяющий результат при {e_h}")
    print(f"Ошибка Рунге Кута {err}")
    err = 10 ** 100

    print("--------------------------------------\nМодифицированный метод Эйлера")
    e_h = h
    while True:
        mod_eiler_points = mod_eiler(func, y, x, xn, e_h, e, n)
        if (err <= e): break
        print(f"Шаг {e_h} оказался недостаточным для заданной точности, уменьшаю шаг {e_h / 2}")
        e_h = e_h / 2
        if e_h == 0:
            break
    print(f"Удовлетворяющий результат при {e_h}")
    print(f"Ошибка Рунге Кута {err}")
    err = 10 ** 100

    print("--------------------------------------\nМетод Милна")
    e_h = h
    while True:
        tx = x
        while tx <= xn:
            can_x.append(tx)
            tx += h
        milne_points = milne(func, y, x, xn, e_h, e, n, h)
        if (err <= e): break
        print(f"Шаг {e_h} оказался недостаточным для заданной точности, уменьшаю шаг {e_h / 2}")
        e_h = e_h / 2
        if e_h == 0:
            break
    print(f"Удовлетворяющий результат при {e_h}")
    err = 10 ** 100
    # Построение графиков
    # Точное решение
    h_exact = h / 100
    x_exact, y_exact = compute_exact_solution_for_graf(func, y, x, xn, h_exact)

    # График
    plt.figure(figsize=(10, 6))
    plt.plot(x_exact, y_exact, 'k-', label='Точное решение', linewidth=2)
    plt.plot(eiler_points[0], eiler_points[1], 'b--o', label='Метод Эйлера', markersize=5)
    plt.plot(mod_eiler_points[0], mod_eiler_points[1], 'r--o', label='Модифицированный метод Эйлера', markersize=5)
    plt.plot(milne_points[0], milne_points[1], 'g--o', label='Метод Милна', markersize=5)
    plt.title('Сравнение точного и приближенных решений')
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid(True)
    plt.legend()
    plt.show()


def compute_exact_solution_for_graf(func, y0, x0, xn, h_exact):
    """Вычисление эталонного решения методом РК4 с малым шагом."""
    n_steps = int((xn - x0) / h_exact) + 1
    return rk4_start(func, y0, x0, h_exact, n_steps)


def compute_exact_solution(func, y0, x0, x_target, h_exact):
    """Вычисление эталонного решения методом РК4 с малым шагом до точки x_target."""
    n_steps = int((x_target - x0) / h_exact) + 1
    x, y = rk4_start(func, y0, x0, h_exact, n_steps)
    return x[-1], y[-1]  # Возвращаем только последнюю точку


def interpolate_exact(x, x_exact, y_exact):
    """Интерполяция эталонного решения для заданной точки x."""
    return np.interp(x, x_exact, y_exact)


def main():
    while (True):
        try:
            chose = int(input("Выберете ОДУ:\n"
                              "1) y' = 1.5x + y^2 + 1.5y\n"
                              "2) y' = sin(x) + cos(y)\n"
                              "3) y' = sin(x) + y\n"))
            if 1 <= chose <= 3:
                y0 = float(input("Введите y0: "))
                x0 = float(input("Введите левую границу x: "))
                xn = float(input("Введите правую границу x: "))
                h = float(input("Введите шаг: "))
                e = float(input("Введите точность: "))
                if chose == 1:
                    solve(f1, y0, x0, xn, h, e, 0)
                elif chose == 2:
                    solve(f2, y0, x0, xn, h, e, 0)
                elif chose == 3:
                    solve(f3, y0, x0, xn, h, e, 0)
            else:
                print("Выберете корректную формулу")
        except Exception as e:
            print(e)
