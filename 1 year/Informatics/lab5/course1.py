i = 0


def dist(s1, s2):
    return [i for i in range(len(s1)) if s1[i] != s2[i]]


def find_covered(cube):
    for ind, k0 in enumerate(k0_cubes):
        for i in range(len(k0)):
            if cube[i] == "X":
                continue
            if k0[i] != cube[i]:
                break
        else:
            print(k0, ind + 1)


def find_next_cubes(k):
    global max_cubes
    nxt = {}
    # print(k)
    used = []
    for i in range(len(k)):
        for j in range(i + 1, len(k)):
            di = dist(k[i], k[j])
            if len(di) == 1:
                nxt_cube = k[i][:di[0]] + "X" + k[i][di[0] + 1:]
                nxt[nxt_cube] = nxt.get(nxt_cube, []) + [f"({i + 1} - {j + 1}),"]
                used.append(i)
                used.append(j)
    for i in range(len(k)):
        if i not in used:
            max_cubes.append(k[i])
    return nxt


cubes = {}
max_cubes = []
k0_cubes = []
for x1 in "01":
    for x2 in "01":
        for x3 in "01":
            for x4 in "01":
                for x5 in "01":
                    # print(f'{i}\t{x1 + x2 + x3 + x4 + x5}\t{x1 + x2 + "0"}\t'
                    #       f'{int(x1 + x2 + "0", 2)}\t{x3 + x4 + x5}'
                    #       f'\t{int(x3 + x4 + x5, 2)}\t{int(x1 + x2 + "0", 2) - int(x3 + x4 + x5, 2)}\t'
                    #       f'{1 if -2 <= int(x1 + x2 + "0", 2) - int(x3 + x4 + x5, 2) <= 1 else "d" if int(x1 + x2 + "0", 2) - int(x3 + x4 + x5, 2) == -3 else 0}')
                    # i += 1
                    # if (int(x1 + x2 + "0", 2) - int(x3 + x4 + x5, 2)) not in [-3, -2, -1, 0, 1]:
                    #     print(x1 + x2 + x3 + x4 + x5)

                    if 3 <= abs((int(x2 + x3, 2) - int(x4 + x5 + x1, 2))) <= 7:
                        # print(x1 + x2 + x3 + x4 + x5)
                        cubes[x1 + x2 + x3 + x4 + x5] = []
                        k0_cubes.append(x1 + x2 + x3 + x4 + x5)

print(len(cubes))
print("\n".join(f"{ind + 1}. {cu} {' '.join(fr)}" for ind, (cu, fr) in enumerate(sorted(cubes.items()))))

for i in range(4):
    print(f"\n{i + 1} cubes: ------------------------")
    # print(k0_cubes)
    cubes = find_next_cubes(sorted(cubes.keys()))
    print(len(cubes))
    # print(k0_cubes)
    print("\n".join(f"{ind + 1}. {cu} {' '.join(fr)}" for ind, (cu, fr) in enumerate(sorted(cubes.items()))))

print("MAX_CUBES:")
for ind, ma in enumerate(max_cubes):
    print(f"{ind + 1}. {ma} COVERED: ")
    find_covered(ma)