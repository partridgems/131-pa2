Michael Partridge
10/26/2014
COSI 131a PA2 Extra Credit

This submission fulfills requirements of parts 1 and 2 and the Extra Credit of the assignment. All tests pass.

In part 1, ant hills use reflection to check the type of an animal trying to eat there and enforce entry restrictions on that animal based on that type. Animals that are rejected from a hill busy wait by continuously checking ant hills until one permitting entry is found.

In part 2, busy waiting is prevented by having a master ant hill (ScheduledAnthill) control the Animals searching for ant hills. An animal is permitted to search through all ant hills only once before being forced to wait. Animals are notified one at a time whenever an animal finishes at an ant hill.

In part 2, priority was handled by keeping a numerical array of all animals waiting for hills by their priority. When a new animal comes to the scheduled ant hill, it first checks for sleeping animals of higher priority by consulting the table of sleeping animals. If there are higher priority animals already waiting, then it waits and does not check ant hills. Before waiting, that animal notifies another animal to try. When an animal finishes eating (calls exitAnthill) that animal calls notify to awaken the next animal. This satisfies the priority handling since animals check their priority against sleeping animals before attempting to eat.

******************************************************************
The extra credit makes the following changes to the existing code:
******************************************************************

IN THE PROVIDED CODE I MADE THE FOLLOWING CHANGE:
I added an eatAnt() method to the Abstract Animal class to decrement the private hunger field.

IN MY OWN CODE I MADE THE FOLLOWING CHANGES:
I added a new Abstract MyAnimal class that all other animals extend. This class allows overriding the (non-final) doWhileAtAnthill() method.

Exterminator colour checking: 
Anthill checks whether incomming animal is exterminator before colour checking.
Anthill only checks that no other exterminators are present.

Exterminators do not enter anthills even if no ants remain.

When an exterminator successfully eats at ScheduledAnthill, prevented consuming ants

When animal enters BasicAnthill (as normal) also save a reference to that animal's thread for later interruption

When exterminator enters BasicAnthill, anthill interrupts all animals by calling a new exterminate() method

When animals are interrupted during timed waiting on anthill, animals begin waiting indefinitely on anthill 
This is implemented via a loop around the original waiting period that waits for a flag to be set indicating that eating has concluded normally. If the animal is interrupted by the exterminator during that period, the animal skips over the flag-setting step in the try block and jumps down to catch, in which the animal waits indefinitely. The animal continues to wait there until it is notified (by the exterminator leaving the anthill) and then loops back to try the timed wait (for eating) again.

I did not collaborate with any person significantly and did not share my code in any way.