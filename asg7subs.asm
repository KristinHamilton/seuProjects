;[asg7subs.asm]
;Kristin Hamilton
;date created: 12-Apr-2014
;description: subroutines for asg7/prog7.asm
;
;cosc2331-01. asg7.
global writeString, newline, readString, isValidHanoiInput,
global charToInt, hanoi, writeHanoiString, intToChar


;SECTION: data, uninitialized
 section .bss
  numString resb 1               ;numString = where char will be saved once int numRings is
                                 ;converted to char. without saving char to .bss variable,
                                 ;get a '-14' bad address errorcode returned in eax from
                                 ;attempt to write to stdout in writeString()

;SECTION: data, initialized
 section .data
 
 ;messages, pieces of writeHanoiString() output
  h1_moveRing db 10, "Move ring "
  len_h1_moveRing equ $ - h1_moveRing
  h2_from db " from "
  len_h2_from equ $ - h2_from
  h3_to db " to "
  len_h3_to equ $ - h3_to
  h4_fullStopCRLF db "."
  len_h4_fullStopCRLF equ $ - h4_fullStopCRLF
  
 ;other data
  linefeed db 10                 ;carriage return/linefeed ("CRLF")


section .text

;void writeString(String str, int strLen)
;  writes message stored in String str, of length strLen, to stdout
;    receives: (String, int) str String address where String of length int strLen is stored
;    returns:  (nothing)
writeString:
    push ebp
    mov ebp, esp
    pushad

    mov edx, [ebp + 12]
    mov ecx, [ebp + 8]

    mov ebx, 1                   ;stdout
    mov eax, 4                   ;write
    int 0x80                     ;interrupt: write String str of length strLen to stdout

    popad
    pop ebp
    ret 8                        ;end writeString()


;void newline(linefeed)
;  writes a newline character/linefeed to stdout
;    receives: ascii character 10 linefeed
;    returns: nothing
newline:
    push ebp
    mov ebp, esp
    pushad

    mov edx, dword 1
    mov ecx, [ebp + 8]
    mov ebx, 1
    mov eax, 4
    int 0x80

    popad
    pop ebp
    ret 4                        ;end newline()


;int readString(String str, int maxChars)
;  reads String with max length of maxChars into String str from stdin
;  and returns the # chars that were read in
;    receives: (String, int) "str a String address that can hold maxChars chars"
;    returns:  (int) length (# chars) of string read in to String str
readString:
    push ebp
    mov ebp, esp
    push edx
    push ecx
    push ebx

    mov edx, [ebp + 12]          ;edx = int max#Chars to read in from stdin
    mov ecx, [ebp + 8]           ;ecx = address of where to save String str
    mov ebx, 2                   ;stdin
    mov eax, 3                   ;read
    int 0x80                     ;interrupt: read String str of length maxChars from stdin

    dec eax                      ;remove newline character from input

    pop ebx
    pop ecx
    pop edx
    pop ebp
    ret 8                        ;end readString()


;int isValidHanoiInput(String numRings, int #chars read in)
;  checks numRings, which is 1 char in length, to verify that it is one of the following:
;  '0', '1', '2', or '3'. if it is, changes eax return value to 1 ("true").
;  otherwise, return value = 0 ("false").
;    receives: (String) String numRings (one char only)
;    returns:  (int) returns 1 ("true") if input is valid. otherwise, returns 0 ("false")
isValidHanoiInput:
    push ebp
    mov ebp, esp
    push edi
    push ecx
    push edx

    xor eax, eax                 ;clear out eax so if input found to be invalid, can just
                                 ;exit subroutine with eax having value of 0 ("false") 
    mov ecx, [ebp + 12]          ;ecx = # chars read in through stdin by readString()
    cmp ecx, 1                   ;if length of input is anything other than 1, it's invalid
    jne popReturn3
    
    mov edi, [ebp + 8]           ;edi = String numRings
    mov dl, [edi]                ;dl = single-char numRings
    
    cmp dl, 48                   ;ascii 48 = '0'
    jl popReturn3
    
    cmp dl, 51                   ;ascii 51 = '3'
    jg popReturn3

    inc eax                      ;eax == 1 ("true," input is valid)
    jmp popReturn3               ;exit subroutine

  popReturn3:
    pop edx
    pop ecx
    pop edi
    pop ebp
    ret 8                        ;end isValidHanoiInput()


;int charToInt(char numRings)
;  converts single-char string to corresponding int. will only be sent one char due to
;  checks in main program. returns the int version of char.
;    receives: (String numRings) String numRings (1 char in length)
;    returns:  (int) returns int value of numRings char
charToInt:
    push ebp
    mov ebp, esp
    push edx
    push ecx
    push esi

    mov ecx, 1                   ;ecx = length of numRings = 1 char.
    mov esi, [ebp + 8]           ;esi = address of char numRings
    xor eax, eax                 ;clear out eax
    xor edx, edx                 ;clear out edx
 
  convertCharToInt:
    mov dl, [esi]                ;load char into dl
    sub dl, 48                   ;convert char to integer (will be one digit)
    add eax, edx                 ;load edx value ("dl - 48") into eax
    jmp popReturn6

  popReturn6:
    pop esi
    pop ecx
    pop edx
    pop ebp
    ret 4                        ;end charToInt()


;void hanoi(int numRings, char a, char b, char c)
;  recursively calls itself if # rings > 1. if numRings == 1, runs once.
;    receives: (int, char, char, char) int numRings, char a (tower1), char b (tower2),
;                                      char c (tower3)
;    returns:  (nothing)
hanoi:
    push ebp
    mov ebp, esp
    pushad
    
    mov ecx, [ebp + 8]           ;ecx = int numRings
    mov eax, [ebp + 12]          ;eax = char a (tower1)
    mov ebx, [ebp + 16]          ;ebx = char b (tower2)
    mov edx, [ebp + 20]          ;edx = char c (tower3)

    test ecx, ecx
    je popReturn0

    cmp ecx, 1                   ;if #rings == 1, we dont want to do any recursive calls
    je oneRing
    
  moreThanOneRing:
    dec ecx                      ;temporarily decrement #rings for first recursive call
    push ebx                     ;start of recursive call one of two
    push edx
    push eax
    push ecx                     
    call hanoi
    inc ecx                      ;restore ecx from "n - 1" back to "n"
    
  oneRing:
    push edx                     ;call writeHanoiString to print line:
    push eax                     ;  "move ring _ from _ to _."
    push ecx
    call writeHanoiString
    
    cmp ecx, 1                   ;check if numRings == 1. if so, go ahead and exit now.
    je popReturn0
    
    dec ecx
    push edx                     ;start of recursive call two of two
    push eax
    push ebx
    push ecx
    call hanoi
    inc ecx    

  popReturn0:
    popad
    pop ebp
    ret 16                       ;end hanoi()


;void writeHanoiString(int ring #, char a, char c)
;  converts int ring # to char, then calls writeString to write hanoi msgs to stdout
;    receives: (int, char, char) int ring number, char a (tower1), char c (tower3)
;    returns:  (nothing)
writeHanoiString:
    push ebp
    mov ebp, esp
    pushad
b4:
    mov ebx, [ebp + 8]           ;ebx = int ring #
   ;convert int ring # back to char so it can be written to stdout.
    push numString
    push ebx
    call intToChar 

   ;load the other arguments into registers
    mov ecx, [ebp + 12]          ;ecx = char a (tower1)
    mov edx, [ebp + 16]          ;edx = char c (tower3)

   ;call writeString to write msgs to stdout.
    push len_h1_moveRing
    push h1_moveRing             ;10, "Move ring "
    call writeString
b5:    
    push dword 1
    push numString               ;"(ring #)"
    call writeString
    
    push len_h2_from
    push h2_from                 ;" from "
    call writeString
    
    push dword 1
    push ecx                     ;"(tower a)"
    call writeString
    
    push len_h3_to
    push h3_to                   ;" to "
    call writeString
    
    push dword 1
    push edx                     ;"(tower c)"
    call writeString
b6:    
    push len_h4_fullStopCRLF
    push h4_fullStopCRLF         ;"."
    call writeString
b7:
    popad
    pop ebp
    ret 12                       ;end writeHanoiString()


;void intToChar(int valueToConvert, addr numString)
;  converts int value to its String/char equivalent, and saves it at addr numString
;    receives: (int) int value to be converted to a char, addr numString address of where
;                    to save char once conversion carried out
;    returns:  (nothing)
intToChar:
    push ebp
    mov ebp, esp
    push edi
    push ebx
    push edx
    
    xor eax, eax
    xor edx, edx                 ;clear out edx in preparation for div operation
    
    mov edi, [ebp + 12]          ;edi = address of numString
    mov eax, [ebp + 8]           ;eax = int value to convert to char
    mov ebx, 10                  ;ebx = 10 for div operation below

    div ebx
    add edx, 48                  ;convert to ascii char by adding 48 to div remainder
    mov [edi], dl                ;load char into numString address

    pop edx
    pop ebx
    pop edi
    pop ebp
    ret 8                        ;end intToChar()
    

;end asg7subs.asm