#!/bin/bash

for dr in `ls grading | grep .cool | grep -v .out`; do
	./lexer "grading/$dr" > "grading-out/$dr.out" 2>&1;

	echo `diff -q grading/$dr.out grading-out/$dr.out` > grade-diff.txt;

	echo "$dr";
done;
