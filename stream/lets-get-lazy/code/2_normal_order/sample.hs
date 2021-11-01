add a b = a + b

evaluate value = value

main = do
  print(evaluate(add 1 2))

--  When is add evaluated? Put a break point on line 1 (char 16) and 3 (char 23) 
-- and step through to see (for steps see the readme.txt file).
-- add is called lazily, after evaluate is called. This is normal order.