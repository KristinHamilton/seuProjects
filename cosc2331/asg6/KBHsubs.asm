;[KBHsubs.asm]
;Kristin Hamilton
;date created: 21-Mar-2014
;description: subroutines for asg1/prog1.asm through asg6/prog6.asm
;
;cosc2331-01.

global newline, writeString, readString, toUpperCase, toLowerCase
global isNumeric, isHexadecimal, stringToInt, findProduct, intToString
global readList, populateList, printList, findMin, findMax, computeAverage, sortList


;SECTION: data, uninitialized
 section .bss

  userString resb 50             ;where to save the array of "ints" before they're
                                 ;converted to actual 4-byte integers
                                 ;this way, printList can just print arrayAsString
  len_userString resd 1          ;length of array when its still a String of characters
  destArray resd 5
  tempArray resd 5

;SECTION: data, initialized
 section .data

 ;prompts
  prompt1EnterList db "You will be prompted to enter up to 5 integers for your array. ", 10
  len_prompt1EnterList equ $ - prompt1EnterList
  genericPrompt db "Please enter an integer or type 'x' to quit.", 10
  len_genericPrompt equ $ - genericPrompt

 ;messages, result of isNumeric subroutine
  notNumericMsg1 db "Your entry was non-numeric.", 10
  len_notNumericMsg1 equ $ - notNumericMsg1

 ;messages, result of isTooLongToStoreAsInt subroutine
  tooLongMsg1 db "Your entry was too long to store as an integer.", 10
  len_tooLongMsg1 equ $ - tooLongMsg1
  
 ;messages, presenters
  listPresenter1 db "Here is your array of integers:", 10
  len_listPresenter1 equ $ - listPresenter1

  emptyListPresenter db "Array is empty."
  len_emptyListPresenter equ $ - emptyListPresenter
  
 ;other data
  linefeed db 10      ;linefeed/carriage return


section .text

;void newline(linefeed)
;  writes a newline character/linefeed to stdout
;    receives: ascii character 10 linefeed
;    returns: nothing
newline:
    push ebp
    mov ebp, esp
    pushad

    mov edx, 1
    mov ecx, [ebp + 8]
    mov ebx, 1
    mov eax, 4
    int 0x80

    popad
    pop ebp
    ret 4                        ;end newline()


;void writeString(String str, int strLen)
;  writes message stored in String str, of length strLen, to stdout
;    receives: (String, int) String address where String of length int strLen is stored
;    returns:  (nothing)
writeString:
    push ebp                  ;ebp = "extended base pointer"
                              ;save current ebp on the stack (setting up "restore point")
    mov ebp, esp              ;esp = "extended stack pointer"
                              ;set up ebp (stack frame) to be used in this subroutine.
                              ;esp now is sitting where ebp is, and marks where the stack
                              ;frame for this method begins.
    pushad                    ;pushad = "push all double." use for void subroutines

    mov edx, [ebp + 12]       ;put the value of the 2nd arg (int strLen) into edx.
                              ;strLen is the length of the String to be written to stdout.
    mov ecx, [ebp + 8]        ;put the value of the 1st arg (String str) into ecx.
                              ;str is the String to be written to stdout.
    mov ebx, 1                ;stdout
    mov eax, 4                ;write
    int 0x80                  ;interrupt: write String str of length strLen to stdout

    popad                     ;restore registers used by this subroutine (putting back
    pop ebp                   ;everything how we found it, memory-wise)
    ret 8                     ;peel the 2 args off the stack:
                              ;  4 bytes per arg * 2 args = 8 bytes
                              ;end writeString()


;int readString(String str, int maxChars)
;  reads String with max length of maxChars into String str from stdin
;  and returns the # chars that were read in
;    receives: (String, int) String address where String of length int maxChars stored.
;    returns:  (int) length (# chars) of string read in to String str
readString:
    push ebp
    mov ebp, esp
    push edx                  ;save whatevers in the extended d, c, and b registers, but
    push ecx                  ;not whats in eax since this subroutine will put the return
    push ebx                  ;value in eax (pushad in writeString was like saying "push
                              ;edx, ecx, ebx, and eax." pushad is just more concise but
                              ;acheives the same result)

    mov edx, [ebp + 12]       ;int maxChars
    mov ecx, [ebp + 8]        ;String str
    mov ebx, 2                ;stdin
    mov eax, 3                ;read
    int 0x80                  ;interrupt: read String str of length maxChars from stdin

    dec eax

    pop ebx                   ;restore registers used by this subroutine
    pop ecx
    pop edx
    pop ebp
    ret 8                     ;peel the 2 args off the stack
                              ;end readString()


;void toUpperCase(String str, int strLen)
;  replaces all lowercase characters in String with uppercase characters
;    receives: (String, int) address str of String, with length int strLen
;    returns:  (nothing)
toUpperCase:
    push ebp
    mov ebp, esp
    pushad

    mov edi, [ebp + 8]
    mov ecx, [ebp + 12]

  checkNextCharLoop1:
    mov al, [edi]
    cmp al, 122                  ;char - 'z' = char - 122
    ja notALowerCaseLetter       ;  if char > 122, not lowercase
    cmp al, 97                   ;char - 'a' = char - 97
    jb notALowerCaseLetter       ;  if char < 97, not lowercase
    sub al, 32                   ;if char is a lowercase letter, convert it to uppercase.
    mov [edi], al                ;NOTE: not moving anything anywhere...doing in-place
                                 ;      modification/capitalization

  notALowerCaseLetter:
    inc edi                      ;move to next char in String
    loop checkNextCharLoop1      ;keep looping while theres more chars in String

    popad
    pop ebp
    ret 8                        ;end toUpperCase()


;void toLowerCase(String str, int strLen)
;  replaces all lowercase characters in String with lowercase characters
;    receives: (String, int) address str of String, with length int strLen
;    returns:  (nothing)
toLowerCase:
    push ebp
    mov ebp, esp
    pushad

    mov edi, [ebp + 8]
    mov ecx, [ebp + 12]

  checkNextCharLoop2:
    mov al, [edi]
    cmp al, 90                   ;char - 'Z' = char - 90
    ja notACapitalLetter         ;  if char > 90, not capital letter
    cmp al, 65                   ;char - 'A' = char - 65
    jb notACapitalLetter         ;  if char < 65, not capital letter
    add al, 32                   ;if char is a capital letter, convert it to lowercase.
    mov [edi], al                ;NOTE: not moving anything anywhere...doing in-place
                                 ;      modification/lowercasing action

  notACapitalLetter:
    inc edi                      ;move to next char in String
    loop checkNextCharLoop2      ;keep looping while theres more chars in String

    popad
    pop ebp
    ret 8                        ;end toLowerCase()


;int isNumeric(String str, int strLen)
;  first, checks 1st char of String str to see whether it is a sign char ('+' or '-').
;  then, checks each remaining char of str to see if it is between '0' and '9', inclusive
;    receives: (String, int) address str of String, with length int strLen
;    returns:  (int) returns true (1 or nonzero) if only numeric characters are found in
;                    String (excluding sign characters, if present). otherwise, returns
;                    false (0)
isNumeric:
    push ebp
    mov ebp, esp
    push ecx
    push edi

    mov ecx, [ebp + 12]          ;int strLen
    mov edi, [ebp + 8]           ;String str

    test ecx, ecx                ;strLen == 0? (test for empty String)
    je notNumeric

  ;test first char to see if it is a '+' or a '-' sign
    cmp [edi], byte '+'
    je signed
    cmp [edi], byte '-'
    je signed

  ;if the first char wasnt a '+' or a '-', proceed with examination of next char
    jmp checkNextCharLoop3

  signed:
    dec ecx                      ;decrement counter manually since were not in a loop here
    test ecx, ecx                ;check value of counter to see if it is already down to 0
    je notNumeric                ;if there isnt a 2nd char in the String, then it isnt
                                 ;numeric (only a '+' or '-' sign), so skip on down
    inc edi                      ;move to 2nd char in String

  ;test remaining chars to see if theyre numeric or not
  checkNextCharLoop3:
    mov al, [edi]
    cmp al, byte '0'             ;char - '0' = char - 48
    jl notNumeric                ;if char < 48
    cmp al, byte '9'             ;char - '9' = char - 57
    jg notNumeric                ;if char > 57

    inc edi                      ;move to next char in String
    loop checkNextCharLoop3      ;keep looping through String until run out of chars
                                 ;(until ecx == 0)
    mov eax, 1                   ;if looped through entire String already, and no invalid
                                 ;(non-numeric) char found, set eax to 1 (indicating "true")
    jmp popReturn3               ;once ecx reaches 0, exit subroutine

  notNumeric:
    mov eax, 0                   ;set eax to 0 (indicating "false")
    jmp popReturn3               ;exit subroutine

  popReturn3:
    pop edi
    pop ecx
    pop ebp
    ret 8                        ;end isNumeric()


;boolean isHexadecimal(String addr)
;  checks each char (6 max) of String addr to see if the char is valid hexadecimal value.
;  valid hex values include 0 to 9 (inclusive), A to F (inclusive), and a to f (inclusive)
;  chars are filtered out by the following compares: the first two checks are to see if
;  the current char is within the overall range of valid hex values (between the ascii
;  values of 48 ('0') and 102 ('f'), if not within that range, subroutine is terminated
;  with eax having been set to "false".  you probably get the idea, there are 6 compares
;  total. if a char has not been weeded out by the end of the compares, eax is set to
;  "true" and subroutine is terminated.
;    receives: (String) String addr of a String
;    returns:  (boolean) "true" if String addr is a valid hexadecimal value.
;              otherwise, returns "false." NOTE: "no sign allowed"
isHexadecimal:
    push ebp
    mov ebp, esp
    push ecx
    push edi

    mov edi, [ebp + 8]
    mov ecx, 6                   ;checking max of 6 chars (hopefully is a good number?)

  checkNextCharLoop4:
    mov al, [edi]
    cmp al, byte '0'             ;char - '0' = char - 48
    jl notValidHexadecimal       ;  if char < 48, not valid: exit
    cmp al, byte 'f'             ;char - 'f' = char - 102
    jg notValidHexadecimal       ;  if char > 102, not valid: exit
    cmp al, byte '9'             ;char - '9' = char - 57
    jle possiblyValid            ;  if char < 57, is valid so far
    cmp al, byte 'a'             ;char - 'a' = char - 97
    jge possiblyValid            ;  if char > 97, is valid so far
    cmp al, byte 'A'             ;char - 'A' = char - 65
    jl notValidHexadecimal       ;  if char < 65, not valid: exit
    cmp al, byte 'F'             ;char - 'F' = char - 70
    jg notValidHexadecimal       ;  if char > 70, not valid: exit
        
  possiblyValid:
    inc edi                      ;move to next char in String
    loop checkNextCharLoop4
    mov eax, 1                   ;if char hasnt been weeded out by now, it is a valid hex,
                                 ;so set return value to "true"
    jmp popReturn4

  notValidHexadecimal:
    mov eax, 0                   ;set return value to false
    jmp popReturn4

  popReturn4:
    pop edi
    pop ecx
    pop ebp
    ret 4                        ;only 1 arg in this method call (4 bytes per arg)
                                 ;end isHexadecimal()


;int stringToInt(StringAddress strAddr, int strLen)
;  expects that main program will perform a call to isNumeric() to verify that String
;  represents an integer. main program will not call stringToInt() on a String that
;  has no numeric equivalent.
;  checks for a sign (+ or -) character, then converts each number character to its
;  integer equivalent. if, as a result of conversion operations, the overflow eflag is
;  set (the bit is turned on), the subroutine is exited. otherwise, returns int userInt.
;    receives: (StringAddress, int) StringAddress strAddr of the String representation
;                    that is to be converted to an int, and int strLen the length of the
;                    String.
;    returns:  (int) int userInt, the numeric representation of the converted String.
stringToInt:
    push ebp
    mov ebp, esp
    push edx
    push ecx
    push ebx
    push esi
    push edi

    xor eax, eax                 ;clear out eax since we'll have to use it for mul later

    mov ecx, [ebp + 12]          ;length of String
    mov esi, [ebp + 8]           ;address of String
    mov ebx, dword 10            ;use ebx register for multiplication by 10
    xor edi, edi                 ;assume positive so edi = 0
    
    test ecx, ecx                ;if length of String = 0
    je zeroString1
 
  ;check for sign
  checkSign:
    mov dl, [esi]                ;load one character into dl from esi
    cmp dl, 43                   ;ascii 43 = '+'
    je signedPositive
    cmp dl, 45                   ;ascii 45 = '-'
    je signedNegative
    jmp convertStringToInt       ;unsigned (positive)

  signedPositive:
    inc esi                      ;advance to next char in String
    dec ecx
                                 ;one char down, ecx - 1 chars left in String
    test ecx, ecx                ;if length of String is 0...
    je zeroString1
    jmp convertStringToInt
   
  signedNegative:
    xor edi, edi                 ;clear edi
    inc edi                      ;edi = 1 to remember negative sign
    inc esi                      ;move to next char
    dec ecx
    test ecx, ecx                ;if length of String is 0, jump down and just move
    je zeroString1               ;a zero into eax and exit subroutine. otherwise, jump
    jmp convertStringToInt       ;down and begin converting each char to an int
        
  convertStringToInt:
  checkNextCharLoop6:
    mov dl, [esi]                ;get a character into dl
    sub dl, 48                   ;convert char to integer (digit)
    add eax, edx                 ;get edx value ("dl - 48") into eax to prepare for mul

    cmp ecx, 1                   ;if only 1 digit left, don't want to multiply again
    je skipMul

    mul ebx                      ;"mul" will multiply whatevers in eax by ebx, then will
                                 ;put the product into edx:eax, meaning that if the
                                 ;product is longer than eax (2 bytes), the product will
                                 ;also be loaded into edx. if product is not that long,
                                 ;then edx will have a value of 0 afterwards, so be
                                 ;mindful here of what you have left in edx (*dl*) before
                                 ;doing the mul operation!
    jo popReturn6                ;if overflow flag is set, then just exit subroutine
    add eax, edx                 ;edx:eax set from mul operation. add them together
    jo popReturn6                ;check again to see if overflow bit has been turned on
        
  skipMul:
    inc esi
    loop checkNextCharLoop6
    jmp negateIfNecessary
      
  negateIfNecessary:
    test edi, edi                ;if(edi == '0'), then original String had no '-' char
    je popReturn6
    neg eax                      ;if(edi == '1'), then negate integer in eax
    jmp popReturn6               ;skip over zeroString1

  zeroString1:                   ;if length of String was 0...
    mov eax, '0'
    jmp popReturn6

  popReturn6:
    pop edi
    pop esi
    pop ebx
    pop ecx
    pop edx
    pop ebp
    ret 8                        ;end stringToInt()


;int findProduct(int userInt, int multiplyByInt)
;  multiplies userint by multiplyByInt. returns the int product.
;    receives: (int, int) int userInt, which is the user-provided String which,
;                    if numeric, has been converted to an int.
;                    int multiplyByInt, the int which userInt is to be multiplied by
;    returns:  (int) product = (int userInt) * (int multiplyByInt)
findProduct:
    push ebp
    mov ebp, esp
    push edx
    push ebx
    
    mov ebx, [ebp + 12]          ;integer to multiply userInt by
    mov eax, [ebp + 8]           ;integer value of userInt
    mul ebx
    test eax, eax                ;if eax is negative, skip the "add eax, edx" step
    jl popReturn7                ;because it winds up giving an incorrect product.

    add eax, edx                 ;edx:eax from mul operation. add them together.
 
  popReturn7:
    pop ebx
    pop edx
    pop ebp
    ret 8                        ;end findProduct()


;int intToString(int valueToConvert, StringAddress strAddr)
;  converts int value to its String equivalent. for asg4, the value being converted is
;  the product returned to the main program by findProduct subroutine. the int returned
;  is the length of the String thats been built once the whole product has been converted
;    receives: (int, StringAddress) int valueToConvert, the integer to be converted to
;                    String representation, and StringAddress strAddr, where the String
;                    representation of the int should be stored
;    returns:  (int) the length of the String that has been converted from an int and
;                    stored at strAddr
intToString:
    push ebp
    mov ebp, esp
    push ebx
    push ecx
    push edx
    push edi
    push esi
    
    xor ecx, ecx                 ;ecx will be used to keep track of how long the String is
bsITS1:    
    mov edi, [ebp + 12]
    mov eax, [ebp + 8]
    test eax, eax                ;int valueToConvert == 0? want to see if int is negative
    jge notNegative8             ;if int >= 0, it is positive. otherwise, it is negative
                                 ;which means that it must have a '-' bit first

bsITS2:
    mov [edi], byte '-'
    inc ecx
    inc edi                      ;prepare to add next character to destination address
    neg eax                      ;get rid of negative sign
    jmp convertIntToString
    
  notNegative8:
    test eax, eax                ;this time, testing to see whether or not int is nonzero
    jne isPositive8
    mov [edi], byte '0'          ;if(eax == 0), put '0' in edi and exit subroutine
    inc ecx
    jmp popReturn8               ;done if String = '0'

  isPositive8:
    mov [edi], byte '+'
    inc ecx
    inc edi
           
  convertIntToString:
    xor esi, esi
    mov ebx, 10    
    
  checkNextCharLoop8a:      
    xor edx, edx                 ;clear out edx to prepare for div operation
    div ebx
    push edx                     ;save edx, remainder of eax/ebx, to stack
    inc esi                      ;esi = '1' to remember remainder on stack
    test eax, eax
    jne checkNextCharLoop8a
        
  checkNextCharLoop8b:
    test esi, esi                ;if there was no remainder from the division operation,
    je popReturn8                ;skip on down to end of subroutine
    pop edx                  
    add edx, 48                  ;convert to ascii character
    mov [edi], dl
    inc edi
    inc ecx
    dec esi
    jmp checkNextCharLoop8b
        
  popReturn8:
    mov eax, ecx                 ;load character count into eax as return value
bsITS3:
    pop esi
    pop edi
    pop edx
    pop ecx
    pop ebx
    pop ebp
    ret 8                        ;end intToString()


;int populateList(address of array of ints, int maxNumberOfIntsToRead)
;  prompts user to enter ints for intArray, one by one. performs numeric and length checks,
;  and converts String to integer. if user input has passed all the validation measures,
;  then and only then will the (now a 4-byte integer) users int be added to intArray, at
;  which point the arrayCount will be incremented. repeats the above until either 5 valid
;  ints have been obtained from the user, or until the user enters an 'x' to quit entry.
;  returns arrayCount.
;    receives: (addr, int) address of intArray, max # ints to store in intArray
;    returns:  (int) # ints actually added to intArray
populateList:
    push ebp
    mov ebp, esp
    push edi
    push esi
    push edx
    push ecx
    push ebx
    
    mov ecx, [ebp + 12]          ;ecx = max# ints to read
    mov edi, [ebp + 8]           ;esi = address of intArray

    xor ebx, ebx                 ;ebx = 0. ebx as counter for # ints stored in intArray.

 ;write initial prompt to stdout
    push len_prompt1EnterList
    push prompt1EnterList        ;"You will be prompted to enter up to 5 ints for your array.", 10
    call writeString

  getIntLoop9:
    push len_genericPrompt
    push genericPrompt           ;"Please enter an integer or type 'x' to quit.", 10
    call writeString             ;prompt to be written to stdout with each loop iteration
        
    push dword 50                ;max characters to read in
    push userString
    call readString
        
  ;load return value (# chars read in) into dword [len_userString]
    mov dword [len_userString], eax
    mov esi, userString          ;esi = address of userString
        
  ;check for an 'x' character (upper- or lowercase), which the user may enter if
  ;they are finished adding ints for their intArray.
    mov dl, [esi]                ;edx = char at current position in userString
        
    cmp dl, byte 'x'
    je saveCount9
    cmp dl, byte 'X'
    je saveCount9
        
  ;numeric check. if isNumeric doesn't return 0 ("false"), skip down and proceed with
  ;subroutine. otherwise, print error msg and jump (NOT loop) to top of loop.
    push dword [len_userString]
    push esi
    call isNumeric
       
    test eax, eax
    jne skipErrorMsg9a
        
    push len_notNumericMsg1
    push notNumericMsg1          ;"Your entry was non-numeric.", 10
    call writeString
        
    jmp getIntLoop9
        
  ;convert String to integer
  skipErrorMsg9a:
    push dword [len_userString]
    push esi
    call stringToInt
       
  ;if overflow flag got set during stringToInt(), then String is too long to store as
  ;int. write error msg to stdout and jump (NOT loop) to top of loop.
    jo tooLong9
         
  ;if user input passed isNumeric() and tooLong checks, go ahead and load it into array         
  saveAsInt9:
    mov [edi], eax               ;load integer returned by stringToInt() into edi
    inc ebx                      ;ebx = ebx + 1 = arrayCount.
    add edi, 4                   ;point to next position in edi (intArray)
    loop getIntLoop9
        
    jmp saveCount9               ;save count once loop has been exhausted.

  tooLong9:
    push len_tooLongMsg1
    push tooLongMsg1             ;"Your entry was too long to store as an integer.", 10
    call writeString
            
    jmp getIntLoop9
        
  ;load ebx into eax so we can return arrayCount to the main program.    
  saveCount9:
    mov eax, ebx

  popReturn9:
    pop ebx
    pop ecx
    pop edx
    pop esi
    pop edi
    pop ebp
    ret 8                        ;end populateList()


;void printList(address of array of ints, number of ints in array)
;  checks count of ints in array. if count = 0 (no ints in array), prints msg ("list
;  empty"). otherwise, loops through intArray, converting one int at a time and writing
;  each int to stdout after it is converted to a String, followed by a newline character.
;    receives: (addr, int) address of integer array, # ints in array
;    returns:  (nothing)
printList:
    push ebp
    mov ebp, esp
    pushad
        
  ;check arrayCount. if list is empty, print msg to that effect and exit subroutine.
  ;otherwise, proceed with program.    
    mov ecx, [ebp + 12]               ;ecx = # ints in array
    test ecx, ecx
    je emptyList10
    
    mov edi, [ebp + 8]                ;edi = address of intArray
    mov ebx, dword [len_userString]   ;ebx = length of userString

  ;now convert each int in array to String, then write intString to stdout
  printLoop:
    mov edx, [edi]               ;edx = current int in array
    xor ebx, ebx                 ;ebx = char count of userString = 0

    push userString              ;where intToString() should load the converted int
    push edx                     ;value to convert (current int in array)
    call intToString
        
    mov ebx, eax                 ;ebx = # chars stored at userString

    push ebx
    push userString
    call writeString             ;write an int to stdout

    push linefeed
    call newline                 ;write a newline character to stdout

    add edi, 4                   ;move pointer to next (4-byte) integer in intArray
    loop printLoop
        
    jmp popReturn10
    
  emptyList10:
    push len_emptyListPresenter         
    push emptyListPresenter      ;"Array is empty."
    call writeString
        
    jmp popReturn10
    
  popReturn10:
    popad
    pop ebp
    ret 8                        ;end printList()


;int findMin(addr of array of ints, number of ints in array)
;  finds and returns smallest int in array
;    receives: (addr, int) address of intArray, # ints in array
;    returns:  (int) smallest int in array
findMin:
    push ebp
    mov ebp, esp
    push edi
    push edx
    push ecx
    push ebx
    
    ;mov eax, 99999              ;sentinel value

    ;check for empty array
    mov ecx, [ebp + 12]          ;ecx = # ints in array
    test ecx, ecx
    je popReturn11
    
    mov edi, [ebp + 8]           ;edi = address of intArray
    mov eax, [edi]               ;eax = smallest int in array.
                                 ;give eax a starting value of intArray[0]

  findMinLoop:
    mov ebx, [edi]               ;ebx = current int in array
    cmp eax, ebx                 ;eax - ebx = 0? (which of the two ints is smaller?)
    jle checkNextInt11           ;if eax <= ebx, leave current value of eax alone
    mov eax, ebx                 ;if eax > ebx, load ebx into eax

  checkNextInt11:
    add edi, 4                   ;point to next int in array
    loop findMinLoop
        
    jmp popReturn11
    
  popReturn11:
    pop ebx
    pop ecx
    pop edx
    pop edi
    pop ebp
    ret 8                        ;end findMin()
        
        
;int findMax(addr of array of ints, number of ints in array)
;  finds and returns largest int in array
;    receives: (addr, int) address of intArray, # ints in array
;    returns:  (int) largest int in array
findMax:
    push ebp
    mov ebp, esp
    push edi
    push edx
    push ecx
    push ebx
    
    ;mov eax, -99999              ;sentinel value

    ;check for empty array
    mov ecx, [ebp + 12]          ;ecx = # ints in array
    test ecx, ecx
    je popReturn12
    
    mov edi, [ebp + 8]           ;edi = address of intArray
    mov eax, [edi]               ;eax = largest int in array.
                                 ;give eax a starting value of intArray[0]

  findMaxLoop:
    mov ebx, [edi]               ;ebx = current int in array
    cmp eax, ebx                 ;eax - ebx = 0? (which of the two ints is larger?)
    jge checkNextInt12           ;if eax >= ebx, leave current value of eax alone
    mov eax, ebx                 ;if eax < ebx, load ebx into eax

  checkNextInt12:
    add edi, 4                   ;point to next int in array
    loop findMaxLoop
        
    jmp popReturn12
    
  popReturn12:
    pop ebx
    pop ecx
    pop edx
    pop edi
    pop ebp
    ret 8                        ;end findMax()
        
        
;int computeAverage(addr of array of ints, number of ints in array)
;  calculates and returns the average (rounded to nearest int) of all ints in array
;    receives: (addr, int) address of intArray, # ints in array
;    returns:  (int) average (rounded to nearest int) of all ints in array
computeAverage:
    push ebp
    mov ebp, esp
    push edi
    push esi
    push edx
    push ecx
    push ebx
    
    xor eax, eax
        
    ;check for empty list
    mov ecx, [ebp + 12]          ;ecx = # ints in array. ecx as loop counter.
    test ecx, ecx
    je popReturn13
    
    mov ebx, [ebp + 12]          ;ebx = # ints in array. ebx as divisor.
    mov edi, [ebp + 8]           ;edi = address of intArray

  sumArrayIntsLoop:
    mov edx, [edi]               ;edx = current int in intArray
    add eax, edx                 ;eax = sum of array. need to have sum in eax for division
    add edi, 4                   ;point to next int in array
    loop sumArrayIntsLoop
        
    xor esi, esi
    cmp eax, 0
    jge sumNotNegative13
    inc esi                      ;esi = 1 to remember that sum of intArray was negative
    
  sumNotNegative13:        
    xor edx, edx                 ;clear out edx for div
    cdq                          ;convert eax to eax sign extended
    idiv ebx                     ;eax / ebx = (sum of ints) / (#ints)
                                 ;now eax holds quotient and edx holds remainder.

    ;for rounding off: max value of remainder = # ints in array - 1.
    ;if remainder can go into divisor twice, round down, since remainder as fraction is
    ;< 0.5. otherwise, round up, since remainder as fraction is >= 0.5.
    ;im doing this without using multiplication (and thereby not messing with eax) by just
    ;adding edx to itself.
    add edx, edx                 ;edx = 2 * edx
    cmp edx, ebx                 ;edx - ebx (compare 2*remainder to original divisor ebx)
    jl checkIfSumWasNegative13
    inc eax                      ;round up for positive intArray sum
    jmp popReturn13
    
  checkIfSumWasNegative13:
    cmp esi, 1                   ;was the sum negative? (esi == 1?)
    jne popReturn13              ;dont round up
    
    neg edx                      ;get rid of remainder's negative sign
    cmp edx, ebx
    jl popReturn13
    dec eax                      ;"round up" for negative intArray sum
    jmp popReturn13
    
  popReturn13:
    pop ebx
    pop ecx
    pop edx
    pop esi
    pop edi
    pop ebp
    ret 8                        ;end computeAverage()


;void sortList(addr of array of ints, number of ints in array)
;  uses insertion sort to sort list of ints
;    receives: (addr, int) address of intArray, # ints in array
;    returns:  (nothing)
sortList:
    push ebp
    mov ebp, esp
    pushad
    
    mov ebx, dword 4
    mov eax, [ebp + 12]
    cmp eax, 1                   ;if #elements in array = 1, its already sorted so exit
    je popReturn13
    
    mul ebx                      ;eax = #elements in array * 4 bytes per element
    xor ebx, ebx
    
    mov esi, [ebp + 8]           ;esi = address of intArray (address of intArray[0])
    mov edi, esi                 ;edi = address of intArray[0]
    add edi, eax                 ;edi = address of intArray[#elements]
    xor eax, eax
    
    add esi, 4
    mov ecx, esi                 ;ecx = address of intArray[1]

  jmpLoop:
    mov eax, [esi]               ;eax = value of current int (starting at intArray[1])
    mov ebx, [esi - 4]           ;ebx = value of previous int (starting at intArray[0])

    cmp eax, ebx                 ;if eax < ebx, need to swap the two values in array.
    jge nextPair                 ;  otherwise, move on to next pair of ints to compare

    cmp esi, ecx                 ;if esi pointing to intArray[0], need to move to next pair
    jl nextPair
    
    mov [esi - 4], eax           ;move current int at intArray[n] to intArray[n - 1]
    mov [esi], ebx               ;move previous int at intArray[n - 1] to intArray[n]
    mov ebx, eax                 ;load value of eax into ebx
    mov eax, [esi]               ;load current int into eax

    sub esi, 4                   ;need to check that first part of array is still sorted
    jmp jmpLoop
    
  nextPair:
    add esi, 4                   ;advance to next element of intArray
    cmp esi, edi                 ;if esi pointing to address just past end of array, done.
    jne jmpLoop                  ;  otherwise, continue with the sort.
    jmp popReturn13
    
  popReturn13:
    popad
    pop ebp
    ret 8                        ;end sortList()  


;end KBHsubs.asm