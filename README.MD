# IPC Demo

This is a small Kotlin program which launches and communicates with a random number generator. One such generator is
implemented in Python in `python/main.py`.

The program prints 100 random numbers from the generator, followed by their average and median.

## Usage

E.g.

```bash
$ ./gradlew build
$ java -jar build/libs/ipc-demo-1.0-SNAPSHOT-all.jar python3 python/main.py
```

```
[1, 9, 11, 20, 33, 49, 55, 71, 81, 86, 89, 145, 166, 180, 208, 212, 219, 225, 234, 237, 239, 249, 250, 269, 282, 296, 298, 301, 305, 314, 329, 340, 341, 352, 356, 359, 377, 383, 384, 386, 387, 390, 391, 395, 395, 397, 404, 405, 408, 415, 417, 425, 458, 468, 509, 529, 533, 550, 563, 563, 567, 572, 576, 584, 596, 598, 613, 637, 638, 657, 663, 687, 703, 705, 711, 719, 726, 726, 740, 761, 766, 770, 778, 813, 826, 837, 843, 853, 857, 872, 873, 882, 888, 890, 907, 924, 955, 984, 986, 996]
484.22
416.0
```

Any errors from the program are forwarded to our own stderr. If we cannot receive a line because the process exited, the
exit code is also given.

```
[Process stderr] Traceback (most recent call last):
[Process stderr]   File ".../python/main.py", line 17, in <module>
[Process stderr]     main()
[Process stderr]   File ".../python/main.py", line 5, in main
[Process stderr]     raise Exception("Crash!")
[Process stderr] Exception: Crash!
Exception in thread "main" java.lang.IllegalStateException: Process exited with code 1 before providing output
        at com.llamalad7.ipcdemo.ProcessScopeImpl.processClosed(ProcessUtils.kt:51)
        at com.llamalad7.ipcdemo.ProcessScopeImpl.receiveLine(ProcessUtils.kt:44)
        at com.llamalad7.ipcdemo.MainKt.main(Main.kt:7)
```
