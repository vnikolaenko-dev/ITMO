from base.custom import custom
from dop.bessel import *
from solve import *
from dop.stirling import stirling_interpolation, plot_stirling_polynomial


def table_input():
    xs = []
    ys = []
    x = float(input("Введите x: "))
    print("Введите exit, чтобы закончить")
    while True:
        try:
            line = input()
            if line == "exit":
                break
            xs.append(list(map(float, line.split(" ")))[0])
            ys.append(list(map(float, line.split(" ")))[1])
        except:
            print("Неверный формат ввода")
    ys_gl.append(ys)
    calculate_dif(ys)

    ans = solve(x, xs, ys)

    correct_s = False
    sti = 0
    try:
        sti = stirling_interpolation(x, xs, ys, None, True)
        plot_stirling_polynomial(x, xs, ys)
        print(f"| Стирлинга             | {sti:10.6f}")
        correct_s = True
    except Exception as err:
        print(err)

    correct_b = False
    bes = 0
    try:
        bes = bessel_interpolation(x, xs, ys, True)
        plot_bessel_polynomial(x, xs, ys)
        print(f"| Бесселя               | {bes:10.6f}")
        correct_b = True
    except Exception as err:
        print(err)

    print("-" * 80)
    print("Результаты:")
    print(f"| Ньютона             | {ans[0]:10.6f}")
    print(f"| Лагранжа            | {ans[1]:10.6f}")
    print(f"| Гаусса              | {ans[2]:10.6f}")
    if correct_s: print(f"| Стирлинга           | {sti:10.6f}")
    if correct_b: print(f"| Бесселя             | {bes:10.6f}")
    print("-" * 80)


def table_input_from_file(filename):
    xs = []
    ys = []
    try:
        with open(filename, 'r') as file:
            # Первая строка - значение x
            x = float(file.readline().strip())

            # Остальные строки - пары значений
            for line in file:
                line = line.strip()
                if line.lower() == 'exit':
                    break
                if line:  # Пропускаем пустые строки
                    parts = list(map(float, line.split()))
                    xs.append(parts[0])
                    ys.append(parts[1])

        ys_gl.append(ys)
        calculate_dif(ys)
        solve(x, xs, ys)

    except FileNotFoundError:
        print(f"Файл {filename} не найден")
    except ValueError:
        print("Неверный формат данных в файле")
    except Exception as e:
        print(f"Произошла ошибка: {e}")


def main():
    while True:
        user_input = input("Выберете способ ввода данных: \n"
                           "table - в формате: x y\n"
                           "file - из файла\n"
                           "custom\n")
        if user_input == "table":
            table_input()
        elif user_input == "file":
            table_input_from_file(input("Введите путь до файла: "))
        elif user_input == "custom":
            custom()
        else:
            print("Недопустимое значение")


if __name__ == "__main__":
    main()
