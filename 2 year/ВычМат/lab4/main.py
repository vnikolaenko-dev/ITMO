import numpy as np
import matplotlib.pyplot as plt
from math import log, exp, sqrt


# Линейная аппроксимация: y = a + b*x
def linear_approximation(xs, ys, n):
    # Суммы для расчета коэффициентов
    sx = sum(xs)  # Сумма x
    sxx = sum(x ** 2 for x in xs)  # Сумма x^2
    sy = sum(ys)  # Сумма y
    sxy = sum(x * y for x, y in zip(xs, ys))  # Сумма x*y

    # Решаем систему уравнений для a и b
    A = np.array([[n, sx], [sx, sxx]])
    B = np.array([sy, sxy])
    a, b = np.linalg.solve(A, B)

    # Возвращаем функцию и коэффициенты
    return lambda x: a + b * x, a, b


# Квадратичная аппроксимация: y = a + b*x + c*x^2
def quadratic_approximation(xs, ys, n):
    # Суммы для расчета коэффициентов
    sx = sum(xs)  # Сумма x
    sxx = sum(x ** 2 for x in xs)  # Сумма x^2
    sxxx = sum(x ** 3 for x in xs)  # Сумма x^3
    sxxxx = sum(x ** 4 for x in xs)  # Сумма x^4
    sy = sum(ys)  # Сумма y
    sxy = sum(x * y for x, y in zip(xs, ys))  # Сумма x*y
    sxxy = sum(x * x * y for x, y in zip(xs, ys))  # Сумма x^2*y

    # Решаем систему уравнений для a, b, c
    A = np.array([[n, sx, sxx], [sx, sxx, sxxx], [sxx, sxxx, sxxxx]])
    B = np.array([sy, sxy, sxxy])
    a, b, c = np.linalg.solve(A, B)

    # Возвращаем функцию и коэффициенты
    return lambda x: a + b * x + c * x ** 2, a, b, c


# Кубическая аппроксимация: y = a + b*x + c*x^2 + d*x^3
def cubic_approximation(xs, ys, n):
    # Суммы для расчета коэффициентов
    sx = sum(xs)  # Сумма x
    sxx = sum(x ** 2 for x in xs)  # Сумма x^2
    sxxx = sum(x ** 3 for x in xs)  # Сумма x^3
    sxxxx = sum(x ** 4 for x in xs)  # Сумма x^4
    sxxxxx = sum(x ** 5 for x in xs)  # Сумма x^5
    sxxxxxx = sum(x ** 6 for x in xs)  # Сумма x^6
    sy = sum(ys)  # Сумма y
    sxy = sum(x * y for x, y in zip(xs, ys))  # Сумма x*y
    sxxy = sum(x * x * y for x, y in zip(xs, ys))  # Сумма x^2*y
    sxxxy = sum(x * x * x * y for x, y in zip(xs, ys))  # Сумма x^3*y

    # Решаем систему уравнений для a, b, c, d
    A = np.array([[n, sx, sxx, sxxx], [sx, sxx, sxxx, sxxxx],
                  [sxx, sxxx, sxxxx, sxxxxx], [sxxx, sxxxx, sxxxxx, sxxxxxx]])
    B = np.array([sy, sxy, sxxy, sxxxy])
    a, b, c, d = np.linalg.solve(A, B)

    # Возвращаем функцию и коэффициенты
    return lambda x: a + b * x + c * x ** 2 + d * x ** 3, a, b, c, d


# Экспоненциальная аппроксимация: y = a * exp(b*x)
def exponential_approximation(xs, ys, n):
    # Применяем логарифм к y, чтобы свести к линейной аппроксимации
    ys_log = [log(y) for y in ys]
    func, a_log, b = linear_approximation(xs, ys_log, n)
    a = exp(a_log)  # Обратное преобразование для a

    # Возвращаем функцию и коэффициенты
    return lambda x: a * exp(b * x), a, b


# Логарифмическая аппроксимация: y = a + b * ln(x)
def logarithmic_approximation(xs, ys, n):
    # Применяем логарифм к x, чтобы свести к линейной аппроксимации
    xs_log = [log(x) for x in xs]
    func, a, b = linear_approximation(xs_log, ys, n)

    # Возвращаем функцию и коэффициенты
    return lambda x: a + b * log(x), a, b


# Степенная аппроксимация: y = a * x^b
def power_approximation(xs, ys, n):
    # Применяем логарифм к x и y, чтобы свести к линейной аппроксимации
    xs_log = [log(x) for x in xs]
    ys_log = [log(y) for y in ys]
    func, a_log, b = linear_approximation(xs_log, ys_log, n)
    a = exp(a_log)  # Обратное преобразование для a

    # Возвращаем функцию и коэффициенты
    return lambda x: a * x ** b, a, b


# Вычисление среднеквадратичной ошибки
def compute_mse(xs, ys, func, n):
    # Среднеквадратичное отклонение
    return sqrt(sum((func(x) - y) ** 2 for x, y in zip(xs, ys)) / n)


# Построение графика лучшей функции
def draw_best_func(func, name, xs, ys):
    # Создаем точки для плавного графика
    x_min, x_max = min(xs) - 0.1, max(xs) + 0.1
    x_smooth = np.linspace(x_min, x_max, 100)
    y_smooth = [func(x) for x in x_smooth]

    # Рисуем точки данных и аппроксимацию
    plt.scatter(xs, ys, label="Данные", color="blue")
    plt.plot(x_smooth, y_smooth, label=f"Лучшая аппроксимация: {name}", color="red")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("Лучшая аппроксимация")
    plt.legend()
    plt.show()


# Получение строки с уравнением функции
def get_func_str(func, coeffs):
    if len(coeffs) == 2:  # Линейная, экспоненциальная, логарифмическая, степенная
        a, b = coeffs
        if func.__name__ == "<lambda>":  # Линейная
            return f"{a:.4f} + {b:.4f}*x"
        elif "exp" in str(func):  # Экспоненциальная
            return f"{a:.4f} * exp({b:.4f}*x)"
        elif "log" in str(func):  # Логарифмическая
            return f"{a:.4f} + {b:.4f}*ln(x)"
        else:  # Степенная
            return f"{a:.4f} * x^{b:.4f}"
    elif len(coeffs) == 3:  # Квадратичная
        a, b, c = coeffs
        return f"{a:.4f} + {b:.4f}*x + {c:.4f}*x^2"
    elif len(coeffs) == 4:  # Кубическая
        a, b, c, d = coeffs
        return f"{a:.4f} + {b:.4f}*x + {c:.4f}*x^2 + {d:.4f}*x^3"


# Основная функция для аппроксимации
def run(functions, xs, ys, n):
    best_mse = float("inf")
    best_func = None
    best_name = ""
    best_coeffs = []

    # Перебираем все функции аппроксимации
    for approx, name in functions:
        try:
            func, *coeffs = approx(xs, ys, n)
            mse = compute_mse(xs, ys, func, n)

            # Сохраняем лучшую функцию по минимальной ошибке
            if mse < best_mse:
                best_mse = mse
                best_func = func
                best_name = name
                best_coeffs = coeffs
        except Exception as e:
            print(f"Ошибка в {name}: {e}")

    # Выводим информацию о лучшей функции
    print(f"Лучшая аппроксимация: {best_name}")
    print(f"Уравнение: f(x) = {get_func_str(best_func, best_coeffs)}")
    print(f"Коэффициенты: {', '.join([f'{c:.4f}' for c in best_coeffs])}")
    print(f"Среднеквадратичная ошибка: {best_mse:.5f}")

    # Рисуем график лучшей функции
    draw_best_func(best_func, best_name, xs, ys)


# Чтение данных из файла
def read_data_from_file(filename):
    try:
        with open(filename, 'r') as file:
            xs, ys = [], []
            for line in file:
                x, y = map(float, line.strip().split())
                xs.append(x)
                ys.append(y)
        return xs, ys
    except Exception as e:
        print(f"Ошибка чтения файла {filename}: {e}")
        return None, None


# Чтение данных с клавиатуры
def read_data_from_input():
    xs, ys = [], []
    print("Введите от 8 до 12 точек (x y) \n 'quit' для завершения:")
    count = 1
    while True:
        inp = input(str(count) + ") ").strip()
        if inp == "quit":
            if count < 9:
                print("Вы ввели недостаточно точек")
            else:
                break
        try:
            x, y = map(float, inp.split())
            xs.append(x)
            ys.append(y)
            count += 1
        except:
            print("Неверный формат, попробуйте еще раз.")
    return xs, ys


# Главная функция
def main():
    # Выбор способа ввода данных
    while True:
        option = input("Введите 'f' (файл), 't' (клавиатура), 'e' (пример): ")
        if option == 'f':
            filename = input("Введите имя файла: ")
            xs, ys = read_data_from_file(filename)
            if xs is None:
                print("Попробуйте ввод с клавиатуры.")
                xs, ys = read_data_from_input()
            break
        elif option == 't':
            xs, ys = read_data_from_input()
            break
        elif option == 'e':
            # Пример данных
            h, x0, n = 0.4, 0, 11
            xs = [round(x0 + i * h, 2) for i in range(n)]
            ys = [round(15 * x / (x ** 4 + 2), 2) for x in xs]
            break
        else:
            print("Неверный ввод, попробуйте еще раз.")

    # Проверка данных
    if not xs or len(xs) != len(ys):
        print("Ошибка: некорректные данные.")
        return

    n = len(xs)

    # Определяем доступные функции аппроксимации
    functions = [(linear_approximation, "Линейная"),
                 (quadratic_approximation, "Квадратичная"),
                 (cubic_approximation, "Кубическая")]

    # Добавляем дополнительные функции, если данные подход, если x > 0
    if all(x > 0 for x in xs):
        if all(y > 0 for y in ys):
            functions.extend([(exponential_approximation, "Экспоненциальная"),
                              (logarithmic_approximation, "Логарифмическая"),
                              (power_approximation, "Степенная")])
        else:
            functions.append((logarithmic_approximation, "Логарифмическая"))

    # Запускаем аппроксимацию
    run(functions, xs, ys, n)


if __name__ == "__main__":
    main()
