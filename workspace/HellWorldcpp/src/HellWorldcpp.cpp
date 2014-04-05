//============================================================================
// Name        : HellWorldcpp.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <time.h>
#include <windows.h>
using namespace std;

int main() {
	cout << "!!!Hello World!!!" << endl; // prints !!!Hello World!!!
	for (int i = 0; i < 4000000; i++){
		cout << "i: " << i << endl;
	}
	Sleep(10000);
	return 0;
}
