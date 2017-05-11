/*Home work 1 CS580U
Name :: Rishikesh Girish Khire
*/

#include <stdio.h>

void function1(){

	printf("Rishikesh Girish Khire\n");			//function printing my name:: Rishikesh Girish Khire.
}

void function2( int x ){

	printf("%d \n",x);					//Function accepting integer  value and printing integer. 

}

void function3( int x , float y ){
	
	printf("%f\n",(x+y));					//Function accepting one integer , one float and adding them. 

}

float function4( float x , float y ){

	float z = x*y;						//Function accepting two float and multiplying them .
	return(z);						// returning result of multiplication.

}

int function5( int x ){
	
	int z = x % 5;						// Function applying Modulo operation.
	return(z);						//returning result
}

float function6 ( float x, float y ){

	float z = y-x;						// Function intake two floats and substract 1st variable from second.
	return(z);						//return result.
}

void function7 ( float x , float y , float m , float n){	

	float z = function4( x , y);				// function call to function4 and return value saved in variable 'z'.
	printf("%f\n", z);					// printing result of function4

	z = function6( m , n);					//function call to function6 and result saved in variable 'z' declared earlier.
	printf("%f\n",z); 					// result printed
}

float function8 ( float m , float n, float k){

	float z = function4( m , n);				// function 4 called and result saved in variable 'z'.
        
        float result = z+k;					// variable result declared to store Sum of z & k.
	return(result);						// Result is  returned.
}


int main(void){							// main function calling function 1,2,3,4,5,6,7,8.

	function1();
	function2( 100 );
	function3( 15 , 3.5 );
	
	float z = function4( 1.11 , 2.00 );
	printf("%f\n",z);

	int m = function5( 11 );
	printf("%d\n",m);

	float n = function6 ( 1.50 , 2.50 );
	printf("%f\n",n);
	
	function7( 2.00 , 3.30 , 3.50 , 6.00);
	
	z = function8 ( 3.00 , 5.00 , 2.50 );
	printf("%f\n",z);

	
return 0;				//returning 0.
}

