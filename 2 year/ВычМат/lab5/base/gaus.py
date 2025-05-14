import numpy as np
import matplotlib.pyplot as plt


def gauss_interpolation(x, xs, ys, show=False):
    """
    Интерполяция Гаусса с использованием разделённых разностей.

    Параметры:
        x (float): Точка, в которой вычисляем значение.
        xs (list): Список узлов интерполяции (x-координаты), должны быть равноотстоящими.
        ys (list): Список значений функции в узлах (y-координаты).
        show (bool): Флаг для отображения промежуточных вычислений.

    Возвращает:
        float: Значение интерполяционного полинома в точке x.
    """
    if show: print("-----------------------------\n"
                   "Интерполяция Гаусса")
    if len(xs) != len(ys):
        raise ValueError("Количество x и y значений должно совпадать!")

    n = len(xs)
    h = xs[1] - xs[0]  # шаг интерполяции (предполагаем равноотстоящие узлы)

    # Проверка на равноотстоящие узлы
    for i in range(1, n - 1):
        if abs((xs[i + 1] - xs[i]) - h) > 1e-10:
            if show: print("Узлы должны быть равноотстоящими для интерполяции Гаусса!")

    # Создаем таблицу разделённых разностей
    F = np.zeros((n, n))
    F[:, 0] = ys

    for i in range(1, n):
        for j in range(n - i):
            F[j, i] = F[j + 1, i - 1] - F[j, i - 1]

    if show:
        print("Таблица разделённых разностей:")
        for row in F:
            print(" ".join([f"{val:.3f}" for val in row]))

    # Выбираем центральный узел в качестве x0
    x0 = xs[n // 2]
    t = (x - x0) / h

    # Вычисляем интерполяционный полином Гаусса
    result = F[n // 2, 0]
    product = 1.0

    for i in range(1, n):
        product *= (t + ((-1) ** (i + 1)) * (i // 2)) / i
        if n // 2 - (i + 1) // 2 < 0 or n // 2 + i // 2 >= n:
            break  # выходим, если выходим за границы таблицы
        result += product * F[n // 2 - (i + 1) // 2, i]

    if show:
        print("Результат:", f"{float(result):.3f}", "\n-----------------------------")

    return result


def plot_gauss_polynomial(x, y, xs, ys, x_min=None, x_max=None, num_points=500):
    """
    Построение графика интерполяционного полинома Гаусса.

    Параметры:
        xs (list): Узлы интерполяции (x-координаты), должны быть равноотстоящими.
        ys (list): Значения функции в узлах (y-координаты).
        x_min (float): Минимальное значение x для графика (по умолчанию min(xs) - 1).
        x_max (float): Максимальное значение x для графика (по умолчанию max(xs) + 1).
        num_points (int): Количество точек для построения графика.
    """
    if x_min is None:
        x_min = min(xs) - min(xs) / 10
    if x_max is None:
        x_max = max(xs) + min(xs) / 10


    # Генерируем точки для построения графика
    x_vals = np.linspace(x_min, x_max, num_points)
    y_vals = [gauss_interpolation(x, xs, ys) for x in x_vals]

    # Настраиваем график
    plt.figure(figsize=(10, 6))
    plt.plot(x_vals, y_vals, label="Интерполяционный полином Гаусса", color="black")
    plt.scatter(xs, ys, color="blue", label="Узлы интерполяции", zorder=5)
    plt.scatter([x], [y], color="red", zorder=5)
    plt.title("График интерполяции Гаусса")
    plt.xlabel("x")
    plt.ylabel("P(x)")
    plt.grid(True, linestyle="--", alpha=0.7)
    plt.legend()
    plt.show()
