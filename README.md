# Sorting-Tool

>**Talented program, that can sorted data using 6 different ways to do it.<br>
You can choose one of two sorting types and one of three data types which you will be able to mix.**

The arguments(sorting type, data type, file for reading data, data output file) we pass like **command line** args:<br>
***By default sorting type - "natular", data type - "word"***

**For example:**<br>
java SortingTool -sortingType byCount -inputFile input.txt<br>
In this example we chose sortin type "byCount" (it means "sorted data by how many time they repead"),<br>
data type don't present so we take default("word"), and get input.txt as path to file from we want to read data.<br>
If -inputFile missing the program **won't stop** until user typed [End-of-Life](https://en.wikipedia.org/wiki/End-of-file) sumbol.<br>

In this example we save output to file which is located in path provided by -outputFile;<br>
java SortingTool -sortingType byCount -inputFile data.dat -outputFile out.txt<br>

Some input output example.<br>
Suppose we have these data as input:<br>
1 -2 &nbsp;&nbsp;&nbsp;  33 4<br>
42<br>
1  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;               1<br>

java SortingTool -sortingType **natural** -dataType **long**<br>
Total numbers: 7<br>
Sorted data: -2 1 1 1 4 42 33<br>

java SortingTool -sortingType natural -dataType **word**<br>
Total words: 7<br>
Sorted data: -2 1 1 1 33 4 42<br>

java SortingTool -sortingType natural -dataType **line**<br>
Total lines: 3<br>
Sorted data:<br>
1                 1<br>
1 -2   33 4<br>
42<br>

<br><br>

java SortingTool -sortingType **byCount** -dataType **long**<br>
Total numbers: 7.<br>
-2: 1 time(s), 14%<br>
4: 1 time(s), 14%<br>
33: 1 time(s), 14%<br>
42: 1 time(s), 14%<br>
1: 3 time(s), 43%<br>

java SortingTool -sortingType byCount -dataType **word**<br>
Total words: 7.<br>
-2: 1 time(s), 14% <br>
33: 1 time(s), 14%<br>
4: 1 time(s), 14%<br>
42: 1 time(s), 14%<br>
1: 3 time(s), 43%<br>

java SortingTool -sortingType byCount -dataType **line**<br>
Here we suppose 42 appears double<br>
Total lines: 4.<br>
1            1: 1 time(s), 25%<br>
1 -2   33 4: 1 time(s), 25%<br>
42: 2 time(s), 50%<br>
