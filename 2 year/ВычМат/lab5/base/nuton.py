import numpy as np
import matplotlib.pyplot as plt


def divided_differences(xs, ys, show=False):
    """
    Вычисление таблицы разделенных разностей для интерполяции Ньютона.

    Параметры:
        xs (list): Список узлов интерполяции (x-координаты).
        ys (list): Список значений функции в узлах (y-координаты).
        show (bool): Флаг для отображения таблицы разностей.

    Возвращает:
        list: Таблица разделенных разностей (первая строка используется для полинома Ньютона).
    """
    n = len(xs)
    if n != len(ys):
        raise ValueError("Количество x и y значений должно совпадать!")

    # Создаем таблицу разделенных разностей
    table = [[0.0] * n for _ in range(n)]
    for i in range(n):
        table[i][0] = ys[i]

    for j in range(1, n):
        for i in range(n - j):
            table[i][j] = (table[i + 1][j - 1] - table[i][j - 1]) / (xs[i + j] - xs[i])

    if show:
        print("Таблица разделенных разностей:")
        for i in range(n):
            print(f"x{i}: {xs[i]:.3f}", end=" | ")
            for j in range(n - i):
                print(f"{table[i][j]:.8f}", end=" ")
            print()

    return table[0]  # Возвращаем первую строку таблицы (для полинома Ньютона)


def newton_polynomial(x, xs, ys, show=False):
    """
    Интерполяция Ньютона с разделенными разностями.

    Параметры:
        x (float): Точка, в которой вычисляем значение.
        xs (list): Список узлов интерполяции (x-координаты).
        ys (list): Список значений функции в узлах (y-координаты).
        show (bool): Флаг для отображения промежуточных вычислений.

    Возвращает:
        float: Значение интерполяционного полинома в точке x.
    """
    if show:
        print("-----------------------------\n"
              "Интерполяция Ньютона")

    coeffs = divided_differences(xs, ys, show)
    n = len(xs)
    result = coeffs[0]
    product_term = 1.0

    if show:
        print("\nВычисление полинома:")
        print(f"P(x) = {coeffs[0]:.3f}", end="")

    for i in range(1, n):
        product_term *= (x - xs[i - 1])
        term = coeffs[i] * product_term
        result += term

        if show:
            op = " + " if term >= 0 else " - "
            print(f"{op}{abs(coeffs[i]):.3f}", end="")
            for j in range(i):
                print(f"(x - {xs[j]:.3f})", end="")

    if show:
        print(f"\nРезультат в точке x={x:.3f}: {result:.3f}")
        print("-----------------------------")

    return result


def plot_newton_polynomial(x, y, xs, ys, x_min=None, x_max=None, num_points=500):
    """
    Построение графика интерполяционного полинома Ньютона.

    Параметры:
        xs (list): Узлы интерполяции (x-координаты).
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
    y_vals = [newton_polynomial(x, xs, ys) for x in x_vals]

    # Настраиваем график
    plt.figure(figsize=(10, 6))
    plt.plot(x_vals, y_vals, label="Интерполяционный полином Ньютона", color="green")
    plt.scatter(xs, ys, color="blue", label="Узлы интерполяции", zorder=5)
    plt.scatter([x], [y], color="red", zorder=5)
    plt.title("График интерполяции Ньютона")
    plt.xlabel("x")
    plt.ylabel("P(x)")
    plt.grid(True, linestyle="--", alpha=0.7)
    plt.legend()
    plt.show()
