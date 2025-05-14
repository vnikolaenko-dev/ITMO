
import matplotlib.pyplot as plt
import numpy as np


def stirling_interpolation(x, xs, ys, mid_idx=None, show=False):
    if show: print("-----------------------------\n"
                   "Интерполяция Стирлинга")

    xs = np.array(xs, dtype=float)
    ys = np.array(ys, dtype=float)

    # Проверка: равноотстоящие узлы
    h = xs[1] - xs[0]
    if not np.allclose(np.diff(xs), h, rtol=1e-5):
        raise ValueError("Узлы xs должны быть равноотстоящими!")

    n = len(xs)
    if n < 2:
        raise ValueError("Должно быть минимум 2 узла интерполяции")

    # Центральный индекс: если не указан, выбираем ближайший к x
    if mid_idx is None:
        mid_idx = np.argmin(np.abs(xs - x))
    t = (x - xs[mid_idx]) / h

    # Центральные разности
    delta = np.zeros((n, n))
    delta[:, 0] = ys
    for j in range(1, n):
        for i in range(n - j):
            delta[i][j] = delta[i + 1][j - 1] - delta[i][j - 1]

    # Инициализация
    result = ys[mid_idx]
    if show:
        print(f"y_0 (центральное): {result:.6f}")

    factorial = 1
    t_term = t  # Для нечетных k: начинаем с t
    t2_term = 1  # Для четных k: начинаем с 1
    k_half = 0

    for k in range(1, n):
        factorial *= k
        i = mid_idx - (k + 1) // 2 + 1  # Корректируем индекс для симметрии

        if k % 2 == 1:  # Нечетный член
            if 0 <= i < n - k and i - 1 >= 0:
                avg = (delta[i][k] + delta[i - 1][k]) / 2
                term = (t_term * avg) / factorial
                result += term
                if show:
                    print(f"Член {k} (нечет): {term:.6f}")
            else:
                if show:
                    print(f"Член {k} (нечет): пропущен")
            # Обновляем множитель для следующего нечетного члена
            if k > 1:
                t_term *= (t * t - k_half * k_half)
            k_half += 1
        else:  # Четный член
            if 0 <= i < n - k:
                t2_term *= (t * t - (k // 2 - 1) ** 2) if k > 2 else t * t
                term = (t2_term * delta[i][k]) / factorial
                result += term
                if show:
                    print(f"Член {k} (чет): {term:.6f}")
            else:
                if show:
                    print(f"Член {k} (чет): пропущен")

    if show:
        print(f"Результат интерполяции: {result:.6f}")

    return result


def plot_stirling_polynomial(x_point, xs, ys, x_min=None, x_max=None, num_points=1000):
    xs = np.array(xs)
    ys = np.array(ys)

    if x_min is None:
        x_min = min(xs) - 0.1 * (max(xs) - min(xs))
    if x_max is None:
        x_max = max(xs) + 0.1 * (max(xs) - min(xs))

    if x_min >= x_max:
        raise ValueError("x_min должен быть меньше x_max!")

    # Фиксируем центральный индекс на основе x_point
    mid_idx = np.argmin(np.abs(xs - x_point))

    # Вычисляем интерполированное значение для x_point
    y_point = stirling_interpolation(x_point, xs, ys, mid_idx=mid_idx)

    # Генерируем точки для графика, используя фиксированный mid_idx
    x_vals = np.linspace(x_min, x_max, num_points)
    y_vals = [stirling_interpolation(x, xs, ys, mid_idx=mid_idx) for x in x_vals]

    # Настраиваем график
    plt.figure(figsize=(10, 6))
    plt.plot(x_vals, y_vals, label="Интерполяционный полином Стирлинга", color="purple")
    plt.scatter(xs, ys, color="blue", label="Узлы интерполяции", zorder=5)

    # Убедимся, что x_point и y_point — скаляры
    x_point_scalar = float(x_point)  # Преобразуем в скаляр
    y_point_scalar = float(y_point)  # Преобразуем в скаляр
    plt.scatter([x_point_scalar], [y_point_scalar], color="red",
                label=f"Точка ({x_point_scalar:.1f}, {y_point_scalar:.1f})", zorder=5)

    # Добавляем аннотации для узлов, преобразуя элементы в скаляры
    for x, y in zip(xs, ys):
        x_scalar = float(x)
        y_scalar = float(y)
        plt.annotate(f"({x_scalar:.1f}, {y_scalar:.1f})", (x_scalar, y_scalar),
                     textcoords="offset points", xytext=(0, 5), ha="center")

    plt.title("Интерполяция Стирлинга")
    plt.xlabel("x")
    plt.ylabel("P(x)")
    plt.grid(True, linestyle="--", alpha=0.7)
    plt.legend()
    plt.show()
