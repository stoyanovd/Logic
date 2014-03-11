#include <cstdio>
#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
#include <time.h>

//#include "bigint/BigIntegerLibrary.hh"

static const int MAX_MINIMIZE = 1000000000;

using namespace std;

typedef long long num;    //TODO       BigInteger

typedef std::vector<num> numList;

typedef num (*func)(numList&);
