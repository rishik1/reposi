#include "fscrypt.h"
#include<stdlib.h>
#include<stdio.h>
#include<string.h>
#include<openssl/blowfish.h>

void* fs_encrypt(void * s, int strln, char *pass, int *len)
{
	char str1[64];
	int key_len = strlen(pass);
	int left;
	unsigned char *ciphertext=(unsigned char *)malloc(100*sizeof(unsigned char));
	memset(ciphertext,'\0',100*sizeof(unsigned char));
	unsigned char *data = (unsigned char*)malloc(key_len*sizeof(unsigned char));
	memcpy(data,pass,key_len);
	//printf("\nkey : %s   and key  length ::  %d",data,key_len);

	BF_KEY *key = (BF_KEY *)malloc(16*sizeof(BF_KEY));
	BF_set_key(key,16,data);

	unsigned char *input =(unsigned char*)malloc(strln*sizeof(unsigned char));
	memcpy(input,(unsigned char*)s,strln); 

	unsigned char *init_vector = (unsigned char *)malloc(8*sizeof(unsigned char));
	int i;
	int n;
	for(i=0;i<8;i++)
	{
		init_vector[i]='0';
	}
	init_vector[i]='\0';
	puts((char *)init_vector);
	strln = strln-1;
	left = (strln)%8;
	left =8-left;
//	printf("\nleft =%d", left);
	if(left !=0)
	{
		i=0;
		for(i=0;i<left;i++)
		{
			input[strln+i]='~';
		}
		input[strln+i]='\0';
	}
	int j=0;
	int total_block = strlen((char *)input);
	printf("\ninput len  %d ",total_block);
	for(j=0;input[j]!='\0';j++)
	{
		printf(" %c ",input[j]);
	}
	 printf("\ninput len  %d ",total_block);
	unsigned char *result = (unsigned char *)malloc(8*sizeof(unsigned char));
	unsigned char *cipher =(unsigned char *)malloc(8*sizeof(unsigned char));
	unsigned long a;
	unsigned long b;
	unsigned long c;
	j=0;
	int k;
	for(i=0;i<8;i++)
        {
                init_vector[i]='0';
        }
        init_vector[i]='\0';

	n = total_block/8;
	while(j<n)
	{
		for(i=0;i<8;i++)
		{
			k= (8*j)+i;
			a =(unsigned long)input[k];
			b =(unsigned long)init_vector[i];
			c =a^b;
			result[i] = (unsigned char)c;
			printf("a ::: %lu \t b :::: %lu \t c :::: %lu \n",a,b,c);
		}
		 BF_ecb_encrypt(result, cipher,key, BF_ENCRYPT);
		//printf("\nresult:::::: %s",cipher);
		memcpy(init_vector,cipher,8);
		strcat((char *)ciphertext,(char *)cipher);
	j++;
	}
	printf("\nciphertext========== %s\n",(char *)ciphertext);
	*len = strlen((char *)ciphertext);

return (ciphertext);
}

void* fs_decrypt(void * outbuf, int len, char *pass, int *recvlen)
{


	char str1[64];
	int key_len = strlen(pass);
	int left;

	 unsigned char *plaintext=(unsigned char *)malloc(100*sizeof(unsigned char));
        memset(plaintext,'\0',100*sizeof(unsigned char));

	unsigned char *data = (unsigned char*)malloc(key_len*sizeof(unsigned char));
	memcpy(data,pass,key_len);
	//printf("\nkey : %s   and key length ::  %d",data,key_len);

	BF_KEY *key = (BF_KEY *)malloc(16*sizeof(BF_KEY));
	BF_set_key(key,16,data);

	unsigned char *input =(unsigned char*)malloc(len*sizeof(unsigned char));
	memcpy(input,(unsigned char*)outbuf,len); 

	unsigned char *init_vector = (unsigned char *)malloc(8*sizeof(unsigned char));
	int i;
	int n;
	for(i=0;i<8;i++)
	{
		init_vector[i]='0';
	}
	init_vector[i]='\0';
	//puts((char *)init_vector);

	int j=0;
	int total_block = strlen((char *)input);
/*	printf("\ninput len  %d ",total_block);
	for(j=0;input[j]!='\0';j++)
	{
		printf(" %c ",input[j]);
	}
	 printf("\ninput len  %d ",total_block);
*/	
	unsigned char *cipherblock = (unsigned char *)malloc(8*sizeof(unsigned char));
	unsigned char *plain =(unsigned char *)malloc(8*sizeof(unsigned char));
	unsigned char *actual_plain =(unsigned char*)malloc(8*sizeof(unsigned char));
	printf("\nplain block::: %s\n",(char *)plain);
	unsigned long a;
	unsigned long b;
	unsigned long c;
	j=0;
	int k;
	n = total_block/8;

	while(j<n)
	{
		k=(j*8);
		memcpy(cipherblock,input+k,8);
		printf("\ncipher block ::: %s\n",(char *)cipherblock);
//		printf("\n plain  ::  %s \n",(char *)plain);
		printf("\n init vect :: %s",(char *)init_vector);
		BF_ecb_encrypt(cipherblock,plain,key,BF_DECRYPT);
		printf("\n plain  ::  %s \n",(char *)plain);


		for(i=0;i<8;i++)
		{
			a=(unsigned long)init_vector[i];
			printf("a ::: %lu\t",a);
			b=(unsigned long)plain[i];
			printf("b  ::: %lu\t",b);
			c= a^b;
			printf(" c:::: %lu\n",c);
			actual_plain[i]=(unsigned char)c;
		}
		printf("\n plain block  :::: %s",actual_plain);
		strcat((char *)plaintext,(char *)actual_plain);
		memcpy(init_vector,cipherblock,8);
	j++;
	}
	k = strlen((char *)plaintext)-1;

	while((char)plaintext[k]=='~')
	{
		k--;
	}
	plaintext[k+1]='\0';
//	printf("\nplaintext :::: %s\n",(char *)plaintext);
	*recvlen = strlen((char *)plaintext)+1;
return ((char *)plaintext);
}

