import numpy as np
import matplotlib.pyplot as plt
def bessel_interpolation(x, xs, ys, show=False) -> float:
    """
    Интерполяция Бесселя для равноотстоящих узлов.

    Args:
        x: Точка, в которой вычисляется значение полинома.
        xs: Список равноотстоящих узлов интерполяции (x-координаты).
        ys: Список значений функции в узлах (y-координаты).
        show: Если True, выводит промежуточные результаты.

    Returns:
        Значение интерполяционного полинома в точке x.

    Raises:
        ValueError: Если списки xs и ys некорректны, узлы не равноотстоящие или содержат дубликаты.
    """
    xs = np.array(xs, dtype=float)
    ys = np.array(ys, dtype=float)

    if show:
        print("-----------------------------\nИнтерполяция Бесселя")

    # Проверка на равноотстоящие узлы
    h = xs[1] - xs[0]
    for i in range(1, len(xs) - 1):
        if not np.isclose(xs[i + 1] - xs[i], h, rtol=1e-5):
            raise ValueError("Узлы xs должны быть равноотстоящими!")

    n = len(xs)
    # Находим центральный интервал
    mid_idx = n // 2
    t = (x - xs[mid_idx]) / h
    if show and (abs(t) < 0.25 or abs(t) > 0.75): raise ValueError("Неподходящий t", f"{t}")

    # Вычисляем центральные разности
    differences = [ys.copy()]
    for order in range(1, n):
        next_diff = []
        for i in range(len(differences[-1]) - 1):
            next_diff.append(differences[-1][i + 1] - differences[-1][i])
        differences.append(next_diff)

    # Формула Бесселя
    y_avg = (ys[mid_idx] + ys[mid_idx + 1]) / 2
    result = y_avg
    if show:
        print(f"Среднее y_{mid_idx}, y_{mid_idx+1}: {y_avg:.3f}")

    # Первый член: t * (Δy_0 + Δy_1)/2
    if len(differences[1]) > mid_idx:
        delta_avg = (differences[1][mid_idx] + differences[1][mid_idx + 1]) / 2 if mid_idx + 1 < len(differences[1]) else differences[1][mid_idx]
        term = t * delta_avg
        result += term
        if show:
            print(f"Член 1 (нечетный): {term:.3f}")

    # Остальные члены
    factorial = 1
    t_term = t * (t - 0.5) if n > 2 else 0  # Начальное значение для k=2
    for k in range(2, n):
        factorial *= k
        idx = mid_idx - k // 2
        if idx < 0 or idx >= len(differences[k]):
            if show:
                print(f"Член {k}: пропущен (недостаточно данных)")
            continue
        if k % 2 == 0:  # Четные члены
            term = differences[k][idx] * t_term / factorial
            result += term
            if show:
                print(f"Член {k} (четный): {term:.3f}")
        else:  # Нечетные члены
            if idx + 1 < len(differences[k]):
                delta_avg = (differences[k][idx] + differences[k][idx + 1]) / 2
                term = delta_avg * t_term / factorial
                result += term
                if show:
                    print(f"Член {k} (нечетный): {term:.3f}")
            else:
                if show:
                    print(f"Член {k} (нечетный): пропущен (недостаточно данных)")
                continue
        # Обновляем множитель
        m = (k + 1) // 2
        t_term *= (t * t - (m - 0.5) * (m - 0.5))

    if show:
        print(f"Результат: {result:.3f}\n-----------------------------")
    return result


def plot_bessel_polynomial(x_point, xs, ys, x_min=None, x_max=None, num_points=500):
    """
    Построение графика интерполяционного полинома Бесселя.

    Args:
        x_point: Точка для отображения на графике.
        xs: Равноотстоящие узлы интерполяции (x-координаты).
        ys: Значения функции в узлах (y-координаты).
        x_min: Минимальное значение x для графика.
        x_max: Максимальное значение x для графика.
        num_points: Количество точек для построения графика.

    Raises:
        ValueError: Если x_min >= x_max или списки xs, ys некорректны.
    """
    # Преобразуем входные данные в массивы
    xs = np.array(xs, dtype=float)
    ys = np.array(ys, dtype=float)
    x_point = float(x_point)  # Убедимся, что x_point — скаляр

    # Проверка размерности
    if xs.ndim != 1 or ys.ndim != 1:
        raise ValueError("xs и ys должны быть одномерными массивами")

    if x_min is None:
        x_min = min(xs) - 0.1 * (max(xs) - min(xs))
    if x_max is None:
        x_max = max(xs) + 0.1 * (max(xs) - min(xs))

    if x_min >= x_max:
        raise ValueError("x_min должен быть меньше x_max!")

    # Вычисляем интерполированное значение для x_point
    y_point = bessel_interpolation(x_point, xs, ys)

    # Генерируем точки для графика
    x_vals = np.linspace(x_min, x_max, num_points)
    y_vals = [bessel_interpolation(x, xs, ys) for x in x_vals]

    # Настраиваем график
    plt.figure(figsize=(10, 6))
    plt.plot(x_vals, y_vals, label="Интерполяционный полином Бесселя", color="purple")
    plt.scatter(xs, ys, color="blue", label="Узлы интерполяции", zorder=5)
    plt.scatter([x_point], [y_point], color="red", label=f"Точка ({x_point:.1f}, {y_point:.1f})", zorder=5)

    # Добавляем аннотации для узлов
    for x, y in zip(xs, ys):
        x_scalar = float(x)  # Преобразуем в скаляр
        y_scalar = float(y)
        plt.annotate(f"({x_scalar:.1f}, {y_scalar:.1f})", (x_scalar, y_scalar),
                     textcoords="offset points", xytext=(0, 5), ha="center")

    plt.title("Интерполяция Бесселя")
    plt.xlabel("x")
    plt.ylabel("P(x)")
    plt.grid(True, linestyle="--", alpha=0.7)
    plt.legend()
    plt.show()