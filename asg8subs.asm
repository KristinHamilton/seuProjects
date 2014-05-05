;[asg8subs.asm]
;Kristin Hamilton
;date created: 22-Apr-2014
;description: subroutines for asg8/main8.c
;
;cosc2331-01.

;%include "pcasm/asm_io.inc"

;SECTION: data, uninitialized
 section .bss

;SECTION: data, initialized
 section .data

  binaryPresenter db "Here's the binary representation: "
  len_binaryPresenter equ $ - binaryPresenter
  
  notSpecialCaseMsg db "Not a special case.", 10
  len_notSpecialCaseMsg equ $ - notSpecialCaseMsg
  specialZeroMsg db "Special case: Zero.", 10
  len_specialZeroMsg equ $ - specialZeroMsg
  specialDenormalizedMsg db "Special case: Denormalized.", 10
  len_specialDenormalizedMsg equ $ - specialDenormalizedMsg
  specialInfinityMsg db "Special case: Infinity.", 10
  len_specialInfinityMsg equ $ - specialInfinityMsg
  specialNaNMsg db "Special case: NaN.", 10
  len_specialNaNMsg equ $ - specialNaNMsg
      
 ;other data    
  zero db 48                                       ;ascii 48 = 0 character
  one db 49                                        ;ascii 49 = 1 character
  space db 32                                      ;ascii 32 = space character
  linefeed db 10                                   ;ascii 10 = linefeed/CR character

  bitmask0 dd 0x80000000         ;1000_0000_0000_0... (-0 or one 1 followed by all zeros)
  bitmask2 dd 0x7F800000         ;0111_1111_1000_0000...


section .text

global newline, writeString
global computeFloatAverage, sortFloatList, printFloatAndDetermineSpecialCase
global printFloat, findSpecialCase, printSpecialCase

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
    push ebp
    mov ebp, esp
    pushad

    mov edx, [ebp + 12]          ;edx = length of String
    mov ecx, [ebp + 8]           ;ecx = address of String
    mov ebx, 1                   ;stdout
    mov eax, 4                   ;write
    int 0x80                     ;write String str of length strLen to stdout

    popad
    pop ebp
    ret 8                        ;end writeString()


;void sortFloatList(addr of array of floats, number of floats in array)
;  uses insertion sort to sort list of floats. interfaces with C main program.
;    receives: (addr, int) address of floatArray, # ints in array
;    returns:  (nothing) returns to C main program.
sortFloatList:
    push ebp
    mov ebp, esp
    pushad
    
    mov ebx, dword 4
    mov eax, [ebp + 12]
    cmp eax, 1                   ;if #elements in array = 1, its already sorted so exit
    je popReturn13
    
    mul ebx                      ;eax = #elements in array * 4 bytes per element
    xor ebx, ebx
    
    mov esi, [ebp + 8]           ;esi = address of floatArray (address of floatArray[0])
    mov edi, esi                 ;edi = address of floatArray[0]
    add edi, eax                 ;edi = address of floatArray[#elements]
    xor eax, eax
    
    add esi, 4
    mov ecx, esi                 ;ecx = address of floatArray[1]

  jmpLoop:
  ;dump_math 1
    fld dword [esi - 4]          ;load floatArray[n - 1] onto top of stack at st0.
  ;dump_math 2
    fld dword [esi]              ;load floatArray[n] onto top of stack at st0.
  ;dump_math 3                   ;floatArray[n - 1] now at st1.

    fcom st1                     ;if st0 < st1, need to swap the two values in array.
    fstsw ax                     ;  otherwise, move on to next pair of floats to compare
    sahf                         ;store result of fcom in ax and shift flags into ah
    jae nextPair

    cmp esi, ecx                 ;if esi pointing to floatArray[0], need to move to next pair
    jl nextPair
    
    ;fxch st1                     ;swap values in st0 and st1 with one another
    fstp dword [esi - 4]          ;move float now in st0 to floatArray[n - 1] then pop off stack
  ;dump_math 4
    fstp dword [esi]              ;move float now in st0 (was in st1) to floatArray[n] then pop
  ;dump_math 5

    sub esi, 4                   ;need to check that first part of array is still sorted
    jmp jmpLoop
    
  nextPair:
    ffree st0                    ;assign st0 as empty
    ffree st1                    ;assign st1 as empty
    add esi, 4                   ;advance to next element of floatArray
    cmp esi, edi                 ;if esi pointing to address just past end of array, done.
    jne jmpLoop                  ;  otherwise, continue with the sort.
    jmp popReturn13
    
  popReturn13:
    popad
    pop ebp
    ret                          ;end sortFloatList()


;float computeFloatAverage(address of floatArray, int floatCount)
;  calculates average of floats in floatArray. interfaces with C main program.
;    receives: (addr, int) address of floatArray, #elements in floatArray
;    returns:  (float) average of elements in floatArray. returns to C main program.
computeFloatAverage:
    push ebp
    mov ebp, esp
    push edi
    push ecx
    
    mov ecx, [ebp + 12]          ;ecx = int floatCount
    test ecx, ecx
    jle popReturn2
    
    fild dword [ebp + 12]        ;st0 = float floatCount
    mov edi, [ebp + 8]           ;edi = address of floatArray[0]

    fld dword [edi]              ;st0 = array[0], st1 = float floatCount
    dec ecx
    test ecx, ecx                ;if original array count == 1, now it will = 0.
    je popReturn2                ;  if that is the case, then average is already in st0.
    
  sumLoop:
    add edi, 4                   ;point to next float element in array
  ;dump_math 1
    fadd dword [edi]             ;st0 = subtotal
  ;dump_math 2
    loop sumLoop
    
  ;dump_math 3
    fdiv st1                     ;st0 = st0 / st1 = totalSum / #elements
  ;dump_math 4

  popReturn2:
    pop ecx
    pop edi
    pop ebp
    ret                       ;end computeFloatAverage()


;void printFloatAndDetermineSpecialCase(float bonusFloat)
;  called from main8.c. prints bonusFloat in decimal and then in binary, and then
;  determines whether bonusFloat represents a special case. prints special case, or prints
;  "not a special case"
;    receives: (float) user-provided float bonusFloat 
;    returns:  (nothing) returns to C main program.
printFloatAndDetermineSpecialCase:
    push ebp
    mov ebp, esp
    pushad

    mov edx, [ebp + 8]           ;edx = bonusFloat
  
    push len_binaryPresenter
    push binaryPresenter         ;"Here's the binary representation: "
    call writeString
  
    push edx
    call printFloat
  
    push linefeed
    call newline
  
    push linefeed
    call newline
  
    xor eax, eax
    
    push edx
    call findSpecialCase
    
    push eax                     ;eax = int specialCaseID returned from findSpecialCase()
    call printSpecialCase

    popad
    pop ebp
    ret                          ;end printFloatAndDetermineSpecialCase()
    

;void printFloat(float)
;  prints binary of float number
;  receives: (float)
;  returns:  (nothing)
printFloat:
    push ebp
    mov ebp, esp
    pushad
    
    mov eax, dword [bitmask0]    ;eax = bitmask0 = 0x80000000
    mov ecx, 32                  ;ecx = bit counter (32 bits per float)
    xor edi, edi
    mov edi, [ebp + 8]           ;edi = float number

    xor ebx, ebx                 ;ebx = print counter, to be cmp'ed against 4

  topLoop:
    cmp ebx, 4                   ;if(# digits printed == 4) print a space
    je printSpace                ;  otherwise, proceed to 'test edi, eax'

    test edx, eax                ;do a bitwise AND of float and bitmask
    jz print0                    ;if(current bit ANDed with in bitmask == 0) print a zero
    jmp print1                   ;  otherwise, print a one
             
  print0:
    push dword 1                 ;# characters to print = 1
    push zero                    ;string to print = "0"
    call writeString
    jmp shiftMask

  print1:
    push dword 1
    push one                     ;string to print = "1"
    call writeString
    jmp shiftMask   
            
  printSpace:
    push dword 1
    push space
    call writeString
    xor ebx, ebx                 ;zero-out ebx (reset print counter) b/c just printed a space
    test ecx, ecx
    jz popReturn3
    jmp topLoop                  ;don't want ecx decremented if we're printing a space
    
  shiftMask:
    inc ebx                      ;we've now printed one character
    shr eax, 1
    loop topLoop
    jmp popReturn3               ;if ecx = 0, exit printFloat subroutine
    
  popReturn3:
    popad
    pop ebp
    ret 4                        ;end printFloat()


;int findSpecialCase(float bonusFloat)
;  determines whether bonusFloat represents a special case.
;    receives: (float) user-provided bonusFloat
;    returns:  (int) specialCaseID. 0 = not a special case. 1 = special case (zero).
;                    2 = special case (denormalized). 3 = special case (infinity).
;                    4 = special case (NaN/"not a number").
findSpecialCase:
    push ebp
    mov ebp, esp
    push edi
    push edx
    push ecx
    push ebx
    
    xor eax, eax                 ;eax = specialCaseID
    mov edi, [ebp + 8]           ;edi = bonusFloat
    mov edx, dword [bitmask2]    ;edx = 0x7F800000 or 0111_1111_1000_0000...

  ;test exponent
    test edi, edx
    jz zeroExponent
    
    and edi, edx
    cmp edi, edx
    je ffExponent
    
    jmp popReturn4               ;exponent != 0x00 && exponent != 0xFF, mantissa = all zeros:
                                 ;  notSpecialCase. exit with eax = specialCaseID = 0.
  zeroExponent:
    mov ecx, 3
  zeroExponentLoop:
    shr edx, 8
    test edi, edx
    jz specialZero
    loop zeroExponentLoop
    jmp specialDenormalized
    
  ffExponent:
    mov ecx, 3
  ffExponentLoop:
    shr edx, 8
    test edi, edx
    jz specialInfinity
    loop ffExponentLoop
    jmp specialNaN

  specialNaN:                    ;specialNaN: will exit with eax = specialCaseID = 4.
    inc eax
  specialInfinity:               ;specialInfinity: will exit with eax = 3.
    inc eax
  specialDenormalized:           ;specialDenormalized: will exit with eax = 2.
    inc eax
  specialZero:                   ;specialZero: will exit with eax = 1.
    inc eax
    
  popReturn4:
    pop ebx
    pop ecx
    pop edx
    pop edi
    pop ebp
    ret 4                        ;end findSpecialCase()
  
    
;void printSpecialCase(int specialCaseID)
;  prints the special case represented by arg int specialCaseID.
;  0 = not a special case. 1 = special case (zero). 2 = special case (denormalized).
;  3 = special case (infinity). 4 = special case (NaN/"not a number").
;    receives: (int) specialCaseID.
;    returns:  (nothing)
printSpecialCase:
    push ebp
    mov ebp, esp
    pushad
    
    mov ebx, [ebp + 8]           ;ebx = specialCaseID
    test ebx, ebx
    je printNotSpecialCase
    
    cmp ebx, 1
    je printSpecialZero
    
    cmp ebx, 2
    je printSpecialDenormalized
    
    cmp ebx, 3
    je printSpecialInfinity
    
    cmp ebx, 4
    je printSpecialNaN
    
  printNotSpecialCase:
    push len_notSpecialCaseMsg
    push notSpecialCaseMsg
    call writeString
    jmp popReturn1
    
  printSpecialZero:
    push len_specialZeroMsg
    push specialZeroMsg
    call writeString
    jmp popReturn1
  
  printSpecialDenormalized:
    push len_specialDenormalizedMsg
    push specialDenormalizedMsg
    call writeString
    jmp popReturn1
  
  printSpecialInfinity:
    push len_specialInfinityMsg
    push specialInfinityMsg
    call writeString
    jmp popReturn1
  
  printSpecialNaN:
    push len_specialNaNMsg
    push specialNaNMsg
    call writeString
    jmp popReturn1
  
  popReturn1:
    popad
    pop ebp
    ret 4                        ;end printSpecialCase()

;end asg8subs.asm
