/* *
 * [main8.c]
 * Author: Kristin Hamilton
 * Desc: main program for asg8. interfaces with asm (asg8subs.asm)
 * Date created: 02-May-2014
 * cosc2331-01. asg8.
 */

#include <stdio.h>
#define MAXSIZE 10  /* max #elements of floatArray */

void printWelcomeMsg();
int getFloatList(float floatArray[]);
void printFloatList(float floatArray[], int floatCount);
void sortFloatList(float floatArray[], int floatCount);
float computeFloatAverage(float floatArray[], int floatCount);
float getBonusFloat();
void printFloatAndDetermineSpecialCase(float bonusFloat);
void printExitMsg();


int main()
{
	float floatArray[MAXSIZE];
	float floatInput = 0.0;
	int floatCount = 0;
	float floatArrayAverage = 0.0;
	float bonusFloat = 0.0;

	printWelcomeMsg();
	floatCount = getFloatList(floatArray);

	printf("\n");
	printf("Here is your original (UNSORTED) list:\n");
	printFloatList(floatArray, floatCount);
	printf("\n");

	if(floatCount > 0)
	{
		sortFloatList(floatArray, floatCount);
		printf("Here is your list, sorted:\n");
		printFloatList(floatArray, floatCount);
		printf("\n");

		floatArrayAverage = computeFloatAverage(floatArray, floatCount);
		printf("Here is the average of your list (formatted to three decimal places): ");
		printf("%11.3f\n", floatArrayAverage);
	}

	printf("\n***** Bonus *****\n");
	bonusFloat = getBonusFloat();
	printf("Here's the original bonusFloat: %f\n", bonusFloat);
	printFloatAndDetermineSpecialCase(bonusFloat);

	printf("\n");
	printExitMsg();

}//end main()

/* *
 * Pre:  (nothing)
 * Post: (nothing)
 * 		 prints welcome msg to stdout. returns nothing.
 */
void printWelcomeMsg()
{
	printf("***********************************\n");
	printf("Welcome to asg8 - Floating Point Sorting and Special Cases!\n");
	printf("\n");
	return;

}//end printWelcomeMsg()

/* *
 * Pre:  (float[])
 * 		 receives float[] floatArray
 * Post: (int)
 * 		 reads in each float entered and saves it to address of floatInput, then adds
 * 		 the float stored at floatInput into floatArray.
 * 		 returns the int floatCount, the number of elements read in and added to floatArray
 */
int getFloatList(float floatArray[])
{
	int floatCount = 0;
	float floatInput = 0.0;

	do
	{
		printf("Please enter a floating point number. Enter '-412' to quit.\n");
		scanf("%f", &floatInput);

		if(floatInput != -412)
		{
			floatArray[floatCount++] = floatInput;
		}
	}
	while(floatCount < MAXSIZE && floatInput != -412);

	return floatCount;

}//end getFloatList()

/* *
 * Pre:  (float[], int)
 * 		 receives address of float[] floatArray, and int floatCount, the number of
 * 		 elements in floatArray.
 * Post: (nothing)
 * 		 writes elements of floatArray to stdout separated by newlines.
 * 		 returns nothing
 */
void printFloatList(float floatArray[], int floatCount)
{
	int i;

	if(floatCount == 0)
	{
		printf("floatArray is Empty.");
		return;
	}

	for(i = 0; i < floatCount; i++)
	{
		printf("%11.3f\n", floatArray[i]);
	}

	return;

}//end printFloatList()

/* *
 * Pre:  (nothing)
 * Post: (float)
 * 		 asks user to input one floating point number, then saves input to bonusFloat.
 * 		 returns bonusFloat.
 */
float getBonusFloat()
{
	float bonusFloat = 0.0;

	printf("Please enter a floating point number: ");
    scanf("%f", &bonusFloat);
	printf("\n");
	return bonusFloat;

}//end getBonusFloat()

/* *
 * Pre:  (nothing)
 * Post: (nothing)
 * 		 prints exit msg to stdout. returns nothing.
 */
void printExitMsg()
{
	printf("Thanks for using the program!\n");
	printf("***********************************\n");
	return;

}//end printExitMsg()

/* end main8.c */
