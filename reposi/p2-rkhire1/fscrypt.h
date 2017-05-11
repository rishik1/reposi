#ifndef FSCRYPT_H_
#define FSCRYPT_H_
void* fs_encrypt(void * s, int strln, char *pass, int *len);
void* fs_decrypt(void * outbuf, int len, char *pass, int *recvlen);
#endif
