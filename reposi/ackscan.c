#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<netdb.h>
#include<netinet/tcp.h>
#include<netinet/ip.h>
#include<errno.h>
#include <arpa/inet.h>

int getLocalAddress ( char * buffer);
void checkPacket(unsigned char* buffer, int size);
unsigned short calculateChecksum(unsigned short * , int );

struct pseudo_header   
{
    unsigned int srcAddress;
    unsigned int destAddress;
    unsigned char placeholder;
    unsigned char protocol;
    unsigned short tcp_length;
    struct tcphdr tcp;
};
struct in_addr destIp;
int main(int argc, char* argv[]){
	
	if(argc < 3)
	{
	    printf("Please specify a hostname and port number \n");
	    exit(1);
	}
	int sockId = socket(AF_INET, SOCK_RAW , IPPROTO_TCP);
	if(sockId==-1){
		printf("%d %s\n",errno, strerror(errno));
	}
	

	char buffer[4096];
	
	//ip header
	struct iphdr* ipHeader = (struct iphdr*)buffer;

	//tcp header
	struct tcphdr* tcpHeader = (struct tcphdr*)(buffer + sizeof(struct ip));
	
	//Socket address for destination
	struct sockaddr_in dest;
	struct in_addr destIp;
	struct pseudo_header psh;
	char *target = argv[1];
	int port = atoi(argv[2]);
	
	if(inet_addr(target)!=-1){
		destIp.s_addr  = inet_addr(target);
	}
	
	//inet_addr function returns an unsigned long value containing a suitable binary representation of the Internet address given
	
	

	char source_ip[30];
	getLocalAddress(source_ip);
	memset(buffer,0,4096);
	
	ipHeader->ihl = 5;
	ipHeader->version = 4;
	ipHeader->tos = 0;
	ipHeader->tot_len = sizeof(struct ip) + sizeof(struct tcphdr);
	ipHeader->id = htons(54321);
	ipHeader->frag_off = htons(16384);
	ipHeader->ttl = 255;
	ipHeader->protocol = IPPROTO_TCP;
	ipHeader->check = 0;
	ipHeader->saddr = inet_addr(source_ip);
	ipHeader->daddr = destIp.s_addr;
	
	int source_port = 45393;
	tcpHeader->source = htons(source_port);
	tcpHeader->dest = htons(port);
	tcpHeader->seq = htonl(100);
	tcpHeader->ack_seq = 0;
	tcpHeader->doff= sizeof(struct tcphdr)/4;
	tcpHeader->fin = 0;
	tcpHeader->syn = 0;
	tcpHeader->ack = 1;
	tcpHeader->rst = 0;
	tcpHeader->psh = 0;
	tcpHeader->urg = 0;
	tcpHeader->window = htons(14600);
	tcpHeader->check = 0;
	tcpHeader->urg_ptr = 0;

	
    int one = 1;
    const int *val = &one;
     /**
     * The setsockopt() function shall set the option specified by the option_name argument, at the protocol level specified by the level argument,
     * to the value pointed to by the option_value argument for the socket associated with the file descriptor specified by the socket argument.
     */
    if (setsockopt (sockId, IPPROTO_IP, IP_HDRINCL, val, sizeof (one)) < 0)
    {
        printf ("Error setting IP_HDRINCL. Error number : %d . Error message : %s \n" , errno , strerror(errno));
        exit(0);
    }
	dest.sin_family = AF_INET;
	dest.sin_addr.s_addr = destIp.s_addr;
	psh.srcAddress = inet_addr( source_ip );
        psh.destAddress = dest.sin_addr.s_addr;
        psh.placeholder = 0;
        psh.protocol = IPPROTO_TCP;
        psh.tcp_length = htons( sizeof(struct tcphdr) );
         
        memcpy(&psh.tcp , tcpHeader , sizeof (struct tcphdr));
         
        tcpHeader->check = calculateChecksum( (unsigned short*) &psh , sizeof (struct pseudo_header));
         
 	
	int result = sendto (sockId, buffer , sizeof(struct iphdr) + sizeof(struct tcphdr) , 0 , (struct sockaddr *) &dest, sizeof (dest));
	
	if ( result< 0)
        {
            printf ("Error sending syn packet. Error number : %d . Error message : %s \n" , errno , strerror(errno));
            exit(0);
        }

	int saddr_size , data_size;
	struct sockaddr saddr;
     
	unsigned char *data = (unsigned char *)malloc(65536); 
     
	
	fflush(stdout);
      
	saddr_size = sizeof saddr;
	data_size = recvfrom(sockId , data , 65536 , 0 , &saddr , &saddr_size);
         
        if(data_size <0 )
        {
            printf("Recvfrom error , failed to get packets\n");
            fflush(stdout);
            return 1;
        }
	
	checkPacket(data,data_size);
	
	return 0;
}

int getLocalAddress ( char * buffer)
{
    int sock = socket ( AF_INET, SOCK_DGRAM, 0);
 
    const char* kGoogleDnsIp = "8.8.8.8";
    int dns_port = 53;
 
    struct sockaddr_in serv;
 
    memset( &serv, 0, sizeof(serv) );
    serv.sin_family = AF_INET;
    serv.sin_addr.s_addr = inet_addr(kGoogleDnsIp);
    serv.sin_port = htons( dns_port );
 
    int err = connect( sock , (const struct sockaddr*) &serv , sizeof(serv) );
 
    struct sockaddr_in name;
    socklen_t namelen = sizeof(name);
    err = getsockname(sock, (struct sockaddr*) &name, &namelen);
 
    inet_ntop(AF_INET, &name.sin_addr, buffer, 100);
 
    close(sock);
}

void checkPacket(unsigned char* buffer, int size)
{
    //Get the IP Header part of this packet
    struct iphdr *iph = (struct iphdr*)buffer;
    struct sockaddr_in source,dest;
    unsigned short iphdrlen;
     
    if(iph->protocol == 6)
    {
        struct iphdr *iph = (struct iphdr *)buffer;
        iphdrlen = iph->ihl*4;
     
        struct tcphdr *tcph=(struct tcphdr*)(buffer + iphdrlen);
             
        memset(&source, 0, sizeof(source));
        source.sin_addr.s_addr = iph->saddr;
     
        memset(&dest, 0, sizeof(dest));
        dest.sin_addr.s_addr = iph->daddr;
	if(tcph->rst==1){
	  printf("Succeed\n");
	}
	else{
	  printf("Fail\n");
	}
    }
}
unsigned short calculateChecksum(unsigned short *ptr,int nbytes) 
{
    register long sum;
    unsigned short oddbyte;
    register short answer;
 
    sum=0;
    while(nbytes>1) {
        sum+=*ptr++;
        nbytes-=2;
    }
    if(nbytes==1) {
        oddbyte=0;
        *((u_char*)&oddbyte)=*(u_char*)ptr;
        sum+=oddbyte;
    }
 
    sum = (sum>>16)+(sum & 0xffff);
    sum = sum + (sum>>16);
    answer=(short)~sum;
     
    return(answer);
}
 
/*
    Get ip from domain name
 */
char* hostname_to_ip(char * hostname)
{
    struct hostent *he;
    struct in_addr **addr_list;
    int i;
         
    if ( (he = gethostbyname( hostname ) ) == NULL) 
    {
        // get the host info
        herror("gethostbyname");
        return NULL;
    }
 
    addr_list = (struct in_addr **) he->h_addr_list;
     
    for(i = 0; addr_list[i] != NULL; i++) 
    {
        //Return the first one;
        return inet_ntoa(*addr_list[i]) ;
    }
     
    return NULL;
}
