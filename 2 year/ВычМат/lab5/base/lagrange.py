import numpy as np
from matplotlib import pyplot as plt


def lagrange(x, xs, ys, show=False):
    """
    Интерполяция Лагранжа.

    Параметры:
        x (float): Точка, в которой вычисляем значение.
        xs (list): Список узлов интерполяции (x-координаты).
        ys (list): Список значений функции в узлах (y-координаты).

    Возвращает:
        float: Значение интерполяционного полинома в точке x.
    """
    if show: print("-----------------------------\n"
                   "Интерполяция Лагранжа")
    if len(xs) != len(ys):
        raise ValueError("Количество x и y значений должно совпадать!")

    result = 0.0
    n = len(xs)

    for i in range(n):
        term = ys[i]  # Начинаем с y_i
        for j in range(n):
            if j != i:
                term *= (x - xs[j]) / (xs[i] - xs[j])  # Домножаем на (x - x_j)/(x_i - x_j)
        if show: print("L" + str(i) + ":", f"{float(term):.3f}")
        result += term
    if show: print("Результат:", f"{float(result):.3f}", "\n-----------------------------")

    return result


def plot_lagrange_polynomial(x, y, xs, ys, x_min=None, x_max=None, num_points=500):
    """
    Построение графика интерполяционного полинома Лагранжа.

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
    y_vals = [lagrange(x, xs, ys) for x in x_vals]

    # Настраиваем график
    plt.figure(figsize=(10, 6))
    plt.plot(x_vals, y_vals, label="Интерполяционный полином Лагранжа", color="purple")
    plt.scatter(xs, ys, color="blue", label="Узлы интерполяции", zorder=5)
    plt.scatter([x], [y], color="red", zorder=5)
    plt.title("График интерполяции Лагранжа")
    plt.xlabel("x")
    plt.ylabel("P(x)")
    plt.grid(True, linestyle="--", alpha=0.7)
    plt.legend()
    plt.show()
