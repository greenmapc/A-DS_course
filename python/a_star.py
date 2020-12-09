import math
from queue import SimpleQueue
import numpy as np
import matplotlib.pyplot as plt

MAP_SIZE = 30
# RIVER_AMOUNT = round(math.sqrt(MAP_SIZE))
# MOUNTIN_AMOUNT = round(math.log(MAP_SIZE))
RIVER_AMOUNT = 2
MOUNTIN_AMOUNT = 2

FIELD_COST = 1
RIVER_COST = 10
MOUNTIN_COST = 20
CHANGE_APPROXIMATION_FUNC_STEPS = MAP_SIZE

MASK = [
    [-1, 1], [0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0]
]
NP_MASK = np.array(MASK)
POINTS_AROUND_AMOUNT = 8

FIG = plt.gcf()
FIG.show()
X_ORD = 0
Y_ORD = 1
COLORS = {
    1: "#1b5e20",
    10: "#0091ea",
    20: "#9e9e9e"
}


def zero_or_val(val):
    return 0 if val == math.inf else val


def vector_2d(p1, p2):
    return [p2[X_ORD] - p1[X_ORD], p2[Y_ORD] - p1[Y_ORD]]


def vector_len(v):
    return math.sqrt(sum(map(lambda x: x ** 2, v)))


def approximated_2d(p1, p2, mapp):
    return vector_len(vector_2d(p1, p2))


def approximated_rounded_2d(p1, p2, mapp):
    return round(vector_len(vector_2d(p1, p2)))


def approximated_mapp_check(p1, p2, mapp):
    step_x = 1 if p2[X_ORD] > p1[X_ORD] else -1
    step_y = 1 if p2[Y_ORD] > p1[Y_ORD] else -1
    cost_1, cost_2 = 0, 0
    for i in range(p1[X_ORD], p2[X_ORD], step_x):
        for j in range(p1[Y_ORD], p2[Y_ORD], step_y):
            cost_1 += mapp[i][j]
    for j in range(p1[Y_ORD], p2[Y_ORD], step_y):
        for i in range(p1[X_ORD], p2[X_ORD], step_x):
            cost_2 += mapp[i][j]
    return min(cost_1, cost_2)


def move(mapp, track, end, curr, approximation=approximated_2d):
    potential_points = np.array(list(map(lambda _: curr + _, NP_MASK)))
    differences = np.full(POINTS_AROUND_AMOUNT, math.inf)
    current_cost = zero_or_val(track[curr[X_ORD]][curr[Y_ORD]])

    for i in range(POINTS_AROUND_AMOUNT):
        p_x, p_y = potential_points[i]
        if p_x < 0 or p_y < 0: continue
        if p_x >= MAP_SIZE or p_y >= MAP_SIZE: continue

        approximated = approximation(potential_points[i], end, mapp)
        differences[i] = mapp[p_x][p_y] + approximated + zero_or_val(track[p_x][p_y])

    min_value = min(differences)
    min_index = np.where(differences == min_value)
    result = [potential_points[i] for i in min_index[0]][0]
    p_x, p_y = result
    track[p_x][p_y] = current_cost + mapp[p_x][p_y]
    return result


def a_star(mapp, start, end, approximation=approximated_2d, drawProgress=False):
    track = np.full((MAP_SIZE, MAP_SIZE), math.inf)
    point_track = []
    # Start
    curr = np.copy(start)
    while (vector_len(vector_2d(curr, end)) != 0):
        point_track.append(curr.tolist())
        curr = move(mapp, track, end, curr, approximation)
        if drawProgress:
            draw(mapp, track, point_track, start, end)
            plt.pause(0.1)

    return point_track, track


def draw(mapp, track, point_track, start, end):
    FIG.clf()
    for i in range(MAP_SIZE):
        for j in range(MAP_SIZE):
            plt.plot(i, j, 's', c=COLORS[mapp[i][j]])
    # Draw choosed way
    for i in point_track:
        plt.plot(i[X_ORD], i[Y_ORD], 'o', c="#ff6f00")
    # Draw start and end points
    plt.plot(start[X_ORD], start[Y_ORD], '<', c="#1a237e")
    plt.plot(end[X_ORD], end[Y_ORD], '>', c="#b71c1c")
    FIG.canvas.draw()


def add_object(mapp, endpoints, cost):
    maps = []
    for temp in endpoints:
        new_mapp = np.copy(mapp)
        r_start, r_end = temp
        point_track, track = a_star(new_mapp, r_start, r_end)
        for i in point_track:
            new_mapp[i[X_ORD]][i[Y_ORD]] = cost
            if i[X_ORD] + 1 < MAP_SIZE: new_mapp[i[X_ORD] + 1][i[Y_ORD]] = cost
            if i[Y_ORD] + 1 < MAP_SIZE: new_mapp[i[X_ORD]][i[Y_ORD] + 1] = cost
        maps.append(new_mapp)
    return join_mapps(maps)


def add_rivers(mapp, rivers):
    return add_object(mapp, rivers, RIVER_COST)


def add_mountins(mapp, mountins):
    return add_object(mapp, mountins, MOUNTIN_COST)


def join_mapps(mapps):
    result_map = np.copy(mapps[0])
    for mapp in mapps[1:]:
        for i in range(MAP_SIZE):
            for j in range(MAP_SIZE):
                result_map[i][j] = max(result_map[i][j], mapp[i][j])
    return result_map


def reversed_index(arr, elem):
    return list(reversed(arr)).index(elem)


def reduce_point_stack(stack):
    # TODO reduce stack
    index = 1
    while index < len(stack):
        elem = stack[index]
        rev_index = reversed_index(stack, elem)
        stack = stack[0:index] + [elem] + stack[len(stack) - rev_index:]
        index += 1
    return stack


if __name__ == "__main__":
    # Init params
    start = np.random.randint(0, MAP_SIZE, 2)
    end = np.random.randint(0, MAP_SIZE, 2)
    # Mapp generating
    mapp = np.full((MAP_SIZE, MAP_SIZE), FIELD_COST, dtype=int)
    rivers = np.random.randint(0, MAP_SIZE, (RIVER_AMOUNT, 2, 2))
    mountins = np.random.randint(0, MAP_SIZE, (MOUNTIN_AMOUNT, 2, 2))
    mapp_rivers = add_rivers(mapp, rivers)
    mapp_mountins = add_mountins(mapp, mountins)
    mapp = join_mapps([mapp_rivers, mapp_mountins])
    # Service variables
    point_track, track = a_star(mapp, start, end, approximation=approximated_2d, drawProgress=False)
    # Draw map
    point_track = reduce_point_stack(point_track)
    draw(mapp, track, point_track, start, end)
    input()