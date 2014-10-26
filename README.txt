Michael Partridge
10/26/2014
COSI 131a PA2 Parts 1 and 2

This submission fulfills requirements of both parts 1 and 2 of the assignment. All tests pass.

In part 1, ant hills use reflection to check the type of an animal trying to eat there and enforce entry restrictions on that animal based on that type. Animals that are rejected from a hill busy wait by continuously checking ant hills until one permitting entry is found.

In part 2, busy waiting is prevented by having a master ant hill (ScheduledAnthill) control the Animals searching for ant hills. An animal is permitted to search through all ant hills only once before being forced to wait. Animals are notified one at a time whenever an animal finishes at an ant hill.

In part 2, priority was handled by keeping a numerical array of all animals waiting for hills by their priority. When a new animal comes to the scheduled ant hill, it first checks for sleeping animals of higher priority by consulting the table of sleeping animals. If there are higher priority animals already waiting, then it waits and does not check ant hills. Before waiting, that animal notifies another animal to try. When an animal finishes eating (calls exitAnthill) that animal calls notify to awaken the next animal. This satisfies the priority handling since animals check their priority against sleeping animals before attempting to eat.

I did not collaborate with any person significantly and did not share my code in any way.