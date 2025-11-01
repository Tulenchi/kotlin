import numpy as np

#interval = [a, b] — поисковый интервал (массив из двух значений в порядке возрастания),
#tol — заданная точность.
#xmin — точка минимума,
#fmin — значение целевой функции в точке минимума,
#neval — число вычислений целевой функции (вычисление производной целевой функции считать также за одно вычисление целевой функции),
#coords —  массив координат x во время оптимизации

def f(x):
    return 2 * (x ** 2) - 9 * x - 31
def df(x):
    return 4 * x - 9

def bsearch(interval, tol):
    a = interval[0]
    b = interval[1]
    neval = 1
    coords = []
    L = b - a  # ширина текущего поискового интервала
    g = df(a)  # производная на левой границе поискового интервала

    while (np.abs(L) > tol) and (np.abs(g) > tol):
        x = (a + b) / 2
        if df(x) > 0:
            b = x
        else:
            a = x
        neval += 1
        coords.append(x)
        L = b - a
        g = df(a)

    xmin = x
    fmin = f(x)

    answer_ = [xmin, fmin, neval, coords]
    #print('Answer = ' + str(answer_))
    return answer_

#interval = [-10, 10]
#tol = 0.001
#bsearch(interval, tol)