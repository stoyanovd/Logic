#include <cstdio>
#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>

#include "bigint/BigIntegerLibrary.hh"

static const int MAX_MINIMIZE = 10000;

using namespace std;

typedef int num;    //TODO       BigInteger

typedef std::vector<num> numList;

typedef num (*func)(numList&);
