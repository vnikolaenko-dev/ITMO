from base.gaus import *
from base.lagrange import *
from base.nuton import *

ys_gl = []


def calculate_dif(ys):
    next_ys = []
    for i in range(len(ys) - 1):
        next_ys.append(ys[i + 1] - ys[i])
    ys_gl.append(next_ys)
    if len(next_ys) == 1:
        print("Конечные разности:")
        n = 0
        for i in ys_gl:
            print(str(n) + ")", "\t".join(map(lambda x: f"{float(x):.4f}", i)))
            n += 1
        return
    calculate_dif(next_ys)


def solve(x, xs, ys):
    # Лагранж
    l = lagrange(x, xs, ys, True)
    # Ньютон
    n = newton_polynomial(x, xs, ys, True)
    # Гаусс
    g = gauss_interpolation(x, xs, ys, True)
    print("-" * 80)
    print("Результаты:")
    print(f"| Ньютона             | {n:10.6f}")
    print(f"| Лагранжа            | {l:10.6f}")
    print(f"| Гаусса              | {g:10.6f}")
    print("-" * 80)

    # Вывод графиков
    print("\nСтроим графики интерполяционных полиномов...")
    plot_lagrange_polynomial(x, l, xs, ys)
    plot_newton_polynomial(x, n, xs, ys)
    plot_gauss_polynomial(x, g, xs, ys)

    return [l, n, g]
