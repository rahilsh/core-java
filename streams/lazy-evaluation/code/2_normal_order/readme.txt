We can use the GHCI REPL to step through the code. Here is the interactive session on the
command line:

> ghci
GHCi, version 7.8.3: http://www.haskell.org/ghc/  :? for help
Loading package ghc-prim ... linking ... done.
Loading package integer-gmp ... linking ... done.
Loading package base ... linking ... done.
λ>:l sample.hs
[1 of 1] Compiling Main             ( sample.hs, interpreted )
Ok, modules loaded: Main.
λ>:break 1 16
Breakpoint 0 activated at sample.hs:1:11-15
λ>:break 3 23
Breakpoint 1 activated at sample.hs:3:18-22
λ>main
Stopped at sample.hs:3:18-22
_result :: Integer = _
value :: Integer = _
λ>:continue
Stopped at sample.hs:1:11-15
_result :: Integer = _
a :: Integer = 1
b :: Integer = 2
λ>:continue
3
it :: ()
λ>

Notice 
Stopped at sample.hs:3:18-22
appeared first and then
Stopped at sample.hs:1:11-15
appeared.

This shows evaluate being called before add. Just the opposite of what we saw in Java.