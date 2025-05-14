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

    try:
        s = stirling_interpolation(x, xs, ys, None, True)
        plot_stirling_polynomial(x, xs, ys)
        print(f"| Стирлинга             | {s:10.6f}")
        ans.append(s)
    except Exception as err:
        print(err)

    try:
        b = bessel_interpolation(x, xs, ys, True)
        plot_bessel_polynomial(x, xs, ys)
        print(f"| Бесселя               | {b:10.6f}")
        ans.append(b)
    except Exception as err:
        print(err)

    print("-" * 80)
    print("Результаты:")
    print(f"| Ньютона             | {ans[0]:10.6f}")
    print(f"| Лагранжа            | {ans[1]:10.6f}")
    print(f"| Гаусса              | {ans[2]:10.6f}")
    print(f"| Стирлинга           | {ans[3]:10.6f}")
    print(f"| Бесселя             | {ans[4]:10.6f}")
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
