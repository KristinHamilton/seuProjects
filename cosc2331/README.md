cosc2331 Introduction to Computer Organization and Architecture<br />
Class projects completed during the Spring 2014 semester.<br />
Programs are written in x86 Assembly; the main program for asg8 is written in C.<br />
<br />
<strong>Summary of Projects.</strong><br />
<ul>
<li><strong><a href = 'https://github.com/KristinHamilton/seuProjects/tree/master/cosc2331/asg5'>
asg5: Integer Array Operations</a></strong><br />
Prompts user to enter integers, one at a time, in order to populate array (max size 5). Handles conversion from String to int and back again. Checks whether user-provided value is numeric, and if so, whether it can be stored as a 4-byte integer.<br />
If there is at least one integer in the array, nasm subroutines find the min and max values, then calculate the average of the array, which is rounded to the nearest int. Results are written to stdout.
<ul><em>
<li><strong>Topics:</strong> Arrays, eflags</li>
</em></ul>
<li><strong><a href = 'https://github.com/KristinHamilton/seuProjects/tree/master/cosc2331/asg6'>
asg6: Integer Array Sorting</a></strong><br />
Uses many of the same subroutines as asg5 (above). Sorts user-provided array of integers via insertion sort. Array may hold a maximum of five ints. Validates user input immediately after reading each value from stdin. Prints both the original and sorted versions of the array.
<ul><em>
<li><strong>Topics:</strong> Sorting, indirect addressing, input validation</li>
</em></ul>
</li>
<li><strong><a href = 'https://github.com/KristinHamilton/seuProjects/tree/master/cosc2331/asg7'>
asg7: Towers of Hanoi</a></strong><br />
Prompts user for an int, 0 to 3 inclusive, as the number of rings to use for the program; user input is validated before advancing through the program.<br />
Uses recursive solution to solve Towers of Hanoi problem, writing each step that needs to be taken to stdout (for example, "Move ring _ from _ to _ .").
<ul><em>
<li><strong>Topics:</strong> Recursion</li>
</em></ul>
</li>
<li><strong><a href = 'https://github.com/KristinHamilton/seuProjects/tree/master/cosc2331/asg8'>
asg8: Floating Point Array Operations</a></strong><br />
Main program written in C; subroutines written in asm.<br />
(Part 1) User prompted to enter up to 10 floats to build array. Array is sorted using insertion sort, and then the average of the float array is calculated. Original (unsorted) array, sorted array, and average are all written to stdout.<br />
(Part 2) User prompted to enter one float. Original float is written to stdout, followed by the binary representation of the original float. Subroutines determine whether the float falls into one of the four IEEE special cases for floating point numbers (zero, denormalized, infinity, NaN), based on the float's bit pattern: if float is a special case, the special case is displayed; otherwise, "not a special case" is displayed.
<ul><em>
<li><strong>Topics:</strong> Single precision floats, arrays, interfacing with C, bit manipulation, numeric coprocessor and floating point stack registers, IEEE special cases, bitmasks</li>
</em></ul>
</li>
</ul>
