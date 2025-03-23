import math
import numpy as np
import matplotlib.pyplot as plt
import sympy as sp

from systems import run

# Объявляем x как символ
x = sp.Symbol('x')

# Общее описание уравнений и их производных
def equation_1(x_val):
    return x_val**3 - 3 * x_val


def equation_2(x_val):
    return (x_val**3 - x_val**2) * math.sin(x_val)


def equation_3(x_val):
    return math.sin(x_val) + 2 * x_val


# Список уравнений для удобного доступа (лямбда-функции для передачи аргумента)
equations = [
    lambda x_val: equation_1(x_val),
    lambda x_val: equation_2(x_val),
    lambda x_val: equation_3(x_val),
]


# Производные уравнений (вычисляем символьно, затем лямбдифицируем)
derivative_1 = sp.lambdify(x, sp.diff(x**3 - 3*x, x), modules=['numpy'])
derivative_2 = sp.lambdify(x, sp.diff((x**3 - x**2) * sp.sin(x), x), modules=['numpy', 'sympy'])
derivative_3 = sp.lambdify(x, sp.diff(sp.sin(x) + 2*x, x), modules=['numpy', 'sympy'])



# Список производных для удобного доступа
derivatives = [derivative_1, derivative_2, derivative_3]


# Вторые производные уравнений (вычисляем символьно, затем лямбдифицируем)
second_derivative_1 = sp.lambdify(x, sp.diff(x**3 - 3*x, x, 2), modules=['numpy'])
second_derivative_2 = sp.lambdify(x, sp.diff((x**3 - x**2) * sp.sin(x), x, 2), modules=['numpy', 'sympy'])
second_derivative_3 = sp.lambdify(x, sp.diff(sp.sin(x) + 2*x, x, 2), modules=['numpy', 'sympy'])


# Список вторых производных для удобного доступа
second_derivatives = [second_derivative_1, second_derivative_2, second_derivative_3]


# Функция для отображения графика функции
def plot_function(f, a, b):
    x = np.linspace(a, b, 100)
    y = [f(xi) for xi in x]

    # Настройка графика
    plt.figure(facecolor="black", figsize=(10, 6))  # Черный фон и размер графика
    plt.plot(x, y, label='Функция', color="pink", linewidth=2)

    # Горизонтальная и вертикальная линии (оси)
    plt.axhline(0, color='white', linewidth=0.5, linestyle='--')
    plt.axvline(0, color='white', linewidth=0.5, linestyle='--')

    # Сетка
    plt.grid(True, linestyle='--', linewidth=0.5, color="black", alpha=0.7)

    # Заголовок и подписи осей
    plt.title("График функции", color="white", fontsize=14, pad=20)
    plt.xlabel("Ось X", color="white", fontsize=12)
    plt.ylabel("Ось Y", color="white", fontsize=12)

    # Легенда
    plt.legend(loc='upper right', fontsize=12, facecolor='black', edgecolor='white', labelcolor='white')

    # Цвета осей и меток
    plt.tick_params(axis='x', colors='white')  # Цвет меток оси X
    plt.tick_params(axis='y', colors='white')  # Цвет меток оси Y

    # Цвет рамки графика
    for spine in plt.gca().spines.values():
        spine.set_color('white')

    plt.legend()
    plt.show()


# Метод хорд (метод секущих)
def secant_method(f, a, b, eps):
    # Проверяем, что функция имеет разные знаки на концах интервала
    if f(a) * f(b) > 0:
        print("Ошибка: на данном интервале нет корня или их несколько.")
        return
    plot_function(f, a, b)

    n = 1
    while True:
        # Вычисляем новое приближение с использованием метода секущих
        x = a - f(a) * (b - a) / (f(b) - f(a))

        print(f"{n} \tx={x};\t f(x)={f(x)};\t {abs(b - a)}")

        # Проверка на сходимость (условие прекращения)
        if abs(f(x)) < eps or abs(b - a) < eps:
            break

        # Обновляем значения для следующей итерации
        if f(a) > 0 and f(x) > 0:
            a = x
        else:
            b = x

        n += 1

    print(f"----Конечный корень: {x}, значение функции в корне: {f(x)}")
    print(f"----Число итераций: {n}")


# Метод Ньютона
def newton_method(f, df, a, b, eps, ddf):
    # Выбор начальной точки x0
    if f(a) * ddf(a) > 0:
        x0 = a
    elif f(b) * ddf(b) > 0:
        x0 = b
    else:
        print("Ошибка: на данном интервале нет корня или их несколько.")
        return
    plot_function(f, a, b)

    if f(a) * f(b) > 0:
        print("Знаки функции на концах интервала одинаковы.")
        return
    elif df(x0) * ddf(x0) > 0:
        print("Условие сходимости не выполняется.")
        return

    n = 0
    while True:
        x = x0 - f(x0) / df(x0)  # Вычисление следующей аппроксимации
        print(f"{n} \t Промежуточное значение x: {x};\t Значение функции в x: {f(x)};")

        # Проверка на завершение вычислений по критерию сходимости
        if (x - x0) <= eps or abs(f(x) / df(x)) <= eps or abs(f(x)) <= eps:
            break

        n += 1
        x0 = x  # Обновление точки для следующей итерации

    print(f"----Конечный корень: {x}, значение функции в корне: {f(x)}")
    print(f"----Число итераций: {n}")


# Метод простой итерации
def simple_iteration_method(f, df, a, b, eps):
    # Проверяем, что f(a) и f(b) имеют разные знаки
    if f(a) * f(b) >= 0:
        print("Ошибка: функция должна иметь разные знаки на концах интервала.")
        return

    # Вычисляем константу для сходимости
    lambda_ = -1 / max(abs(df(a)), abs(df(b)))  # Выбор lambda для сходимости
    print(f"Используем lambda = {lambda_} для сходимости.")

    # Функция итераций
    def phi(x):
        return x + lambda_ * f(x)

    # Проверка сходимости
    def d_phi(x):
        return 1 + lambda_ * df(x)

    # Проверяем, что |phi'(x)| < 1 на интервале [a, b]
    if max(abs(d_phi(a)), abs(d_phi(b))) >= 1:
        print("ВНИМАНИЕ: Условие сходимости не выполнено. Последовательность может не сойтись.")
        print("Хотите продолжить? [y/n]")
        answer = input().strip().lower()
        if answer != "y":
            return

    # Выбор начального приближения
    x0 = (a + b) / 2  # Начальное приближение — середина интервала
    print(f"Метод простой итерации. Начальное приближение: x0 = {x0}")

    # Итерационный процесс
    n = 0
    while n < 100:
        x1 = phi(x0)  # Следующее приближение
        print(f"{n}\t x0 = {x0:.6f},\t x1 = {x1:.6f},\t |x1 - x0| = {abs(x1 - x0):.6f}")

        # Проверка на завершение
        if abs(x1 - x0) < eps:
            print(f"----Конечный корень: {x1:.6f}, значение функции в корне: {f(x1):.6f}")
            print(f"----Число итераций: {n}")
            return x1

        x0 = x1  # Обновление x0
        n += 1

    print("Достигнут лимит итераций, последовательность не сошлась.")
    return None


# Функция для чтения данных из файла
def get_input_from_file(filename):
    try:
        with open(filename, 'r') as file:
            data = file.readlines()
            return [float(value.strip()) for value in data]
    except Exception as e:
        print(f"Ошибка чтения файла: {e}")
        return None


# Функция для получения данных от пользователя (ввод с клавиатуры или из файла)
def get_user_input():
    choice = input("Введите имя файла для загрузки данных или оставьте пустым для ручного ввода: ").strip()
    if choice:
        return get_input_from_file(choice)
    else:
        return [
            float(input("Введите левую границу интервала: ")),
            float(input("Введите правую границу интервала: ")),
            float(input("Введите погрешность вычисления: "))
        ]


# Основная функция, которая запускает программу
def main():
    while True:
        print("Выберите тип программы:")
        print("1: Нелинейное уравнение")
        print("2: Система нелинейных уравнений")
        print("3: Выход")
        prog_type = input("Введите номер типа: ")

        if prog_type == "3":
            break
        if prog_type == "2":
            run()  # Запуск системы нелинейных уравнений
            continue

        print("Выберите уравнение:")
        print("1: x^3 - 3*x")
        print("2: (x^3 - x^2) * math.sin(x)")
        print("3: sin(x) + 2x")

        eq_choice = input("Введите номер уравнения: ")

        # Получение функции и производных для выбранного уравнения
        f = equations[int(eq_choice) - 1]
        df = derivatives[int(eq_choice) - 1]
        ddf = second_derivatives[int(eq_choice) - 1]

        plot_function(f, -50, 50)

        print("Выберите метод:")
        print("1: Метод хорд ")
        print("2: Метод Ньютона")
        print("3: Метод простой итерации")
        method_choice = input("Введите номер метода: ")

        input_data = get_user_input()
        if not input_data:
            continue

        # Выбор метода и его выполнение
        if method_choice == "1":
            secant_method(f, input_data[0], input_data[1], input_data[2])
        elif method_choice == "2":
            newton_method(f, df, input_data[0], input_data[1], input_data[2], ddf)
        elif method_choice == "3":
            simple_iteration_method(f, df, input_data[0], input_data[1], input_data[2])
        else:
            print("Некорректный выбор метода.")
            continue

        # Спрашиваем, нужно ли выполнить программу еще раз
        again = input("Еще раз? [y/n]: ")
        if again.lower() != "y":
            break


main()
