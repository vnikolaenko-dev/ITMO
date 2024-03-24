ORG		0x10
STR:	WORD	0x0070
STOP:	WORD 	0x0000
MARK:	WORD	0
START:	CLA				; старт программы

S1:	IN		0x1D		; чтение из ВУ по адресу в аккумулятор
	AND 	#0x40		; сравниваем 6 бит
	BEQ 	S1			; если нет доступа - спин-луп
	IN		0x1C
	CMP		N1
	BPL		S1
	ST 		PROM
	ST 		W1
S2:	IN		0x1D		; чтение из ВУ по адресу в аккумулятор
	AND 	#0x40		; сравниваем 6 бит
	BEQ 	S2			; если нет доступа - спин-луп
	IN		0x1C
	CMP		#0xF		; проверка на равно
	BEQ 	S4			;
	CMP		N1
	BPL		S2
	ST 		W2
	LD		W1
	ASL
	ASL
	ASL
	ADD		W1
	ADD		W1
	ADD		W2
	ST		PROM
S3:	IN		0x1D		; чтение из ВУ по адресу в аккумулятор
	AND 	#0x40		; сравниваем 6 бит
	BEQ 	S3			; если нет доступа - спин-луп
	IN		0x1C
	CMP		#0xF
	BNE 	S3			; если не = - спин-луп
S4:	LD		PROM
	CLC
	ROR
	ROR
	ROR
	ROR
	CLC
	ROR
	ASR
	ASR
	ASR
	ASR
	ASR
	ASR
	ASR
	ASR
	ASR
	ASR
	ASR
	ASR
	ST 		(STR)+
	LD		PROM
	ASR
	ASR
	ASR
	ST 		PROM
	LD 		MARK
	INC
	ST 		MARK
	LD		PROM
	BNE		S4
S5:	IN		0x15		; чтение из ВУ по адресу в аккумулятор
	AND 	#0x40		; сравниваем 6 бит
	BEQ 	S5			; если нет доступа - спин-луп
	LD		MARK
	DEC
	BMI		PHLT
	ST 		MARK
	ASL
	ASL
	ASL
	ASL
	ADD		-(STR)		; загружаем значение в аккумулятор (косвенная)
	OUT		0x14 		; загружаем значение из аккумулятора в ВУ по адресу
	BNE		S5			; если стоп слово - завершаем работу
PHLT:	HLT
HLT


ORG		0x90
RES:	WORD	?
PROM:	WORD	?
OST: 	WORD	?
N1: 	WORD	0xA
N2:		WORD	0xAA
N3: 	WORD	0x8000

W1:		WORD	?
W2:		WORD	?

ORG		0x70
C1:		WORD	?
C2:		WORD	?
C3:		WORD	?