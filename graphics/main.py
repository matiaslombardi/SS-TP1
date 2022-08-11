import numpy as np
import matplotlib.pyplot as plt

particles = []
with open("../src/main/resources/static1.txt", "r") as static_file:
    particle_count = int(static_file.readline())
    space_size = float(static_file.readline())
    for idx in range(particle_count):
        [radius, _] = [float(n) for n in static_file.readline().split()]
        particles.append({"id": idx + 1, "radius": radius})
    static_file.close()

with open("../src/main/resources/dynamic1.txt", "r") as dynamic_file:
    dynamic_file.readline()
    for idx in range(particle_count):
        [x, y] = [float(n) for n in dynamic_file.readline().split()]
        particles[idx]["x"] = x
        particles[idx]["y"] = y
    
    dynamic_file.close()

with open("../out.txt", "r") as out_file:
    for idx in range(particle_count):
        parts = [int(n) for n in out_file.readline().split()]
        if len(parts) == 1:
            particles[idx]["neighbours"] = []
            continue

        neighbours = parts[1:]
        particles[idx]["neighbours"] = neighbours

    out_file.close()

selected = int(input("Enter a number: "))
print(particles[selected]["neighbours"]) # [99, 100, 69, 41, 76, 13, 16, 81, 19, 83, 84, 22, 25, 91, 28, 93, 63]
with open("ovito.txt", "w") as ovito_file:
    ovito_file.write(str(particle_count) + '\n')
    ovito_file.write("Title\n")
    for particle in particles:
        category = "NN"
        if particle["id"] == selected:
            category = "S"
        elif particle["id"] in particles[selected - 1]["neighbours"]:
            category = "N"
        ovito_file.write(f"{particle['radius']} {particle['x']} {particle['y']} {category}\n")
    ovito_file.close()


x = [particle["x"] for particle in particles]
y = [particle["y"] for particle in particles]

plt.scatter(x, y)
plt.show()
    

