# cryptosolver
hi

[cryptogram solver for simple subsitution ciphers]

uses n-gram analysis (mainly letter bigrams) to evaluate certain key states through table swapping, and has optimization options of simulated annealing, stochastic hill climbing, 2-depth best neighbor hill climbing, and a few more. Right now 2-depth best neighbor generally works the best, though depending on the ciphertext, others could work better. 

currently the program works relatively well, but can be heavily improved. word n-grams and punishment for bad keys are ideas to be tested in the future.

-still in heavy development-


# references
1. Kun, J. (2014, April 15). Cryptanalysis with n-grams. Math ∩ Programming. Retrieved February 1, 2023, from https://jeremykun.com/2012/02/03/cryptanalysis-with-n-grams/
2.  Thomas Jakobsen (1995) A FAST METHOD FOR CRYPTANALYSIS OF SUBSTITUTION CIPHERS, CRYPTOLOGIA, 19:3, 265-274, DOI: 10.1080/0161-119591883944
3. Edwin Olson (2007) Robust Dictionary Attack of Short Simple Substitution Ciphers, Cryptologia, 31:4, 332-342, DOI: 10.1080/01611190701272369

