#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<netdb.h>
#include<netinet/tcp.h>
#include<netinet/ip.h>
#include<errno.h>
#include <arpa/inet.h>

void analysePacket(unsigned char* buffer, int size);
int localAddress ( char * buffer);
unsigned short find_Checksum(unsigned short * , int );
struct in_addr destIp;

struct p_header   
{
    unsigned int source_Address;
    unsigned int d_Address;
    unsigned char placeholder;
    unsigned char protocol;
    unsigned short tcp_length;
    struct tcphdr tcp;
};

int main(int argc, char* argv[]){
	
	if(argc <= 2)
	{
	    printf("Please enter proper arguments \n");
	    exit(1);
	}
	int socket_id = socket(AF_INET, SOCK_RAW , IPPROTO_TCP);
	if(socket_id==-1){
		printf("%d %s\n",errno, strerror(errno));
	}

	char buffer[4096];
	struct iphdr* ip_header = (struct iphdr*)buffer;
	struct tcphdr* tcp_header = (struct tcphdr*)(buffer + sizeof(struct ip));
	struct sockaddr_in dest;
	struct in_addr destIp;
	struct p_header psh;
	char *target = argv[1];
	int port = atoi(argv[2]);
	char source_ip[30];
	if(inet_addr(target)!=-1){
		destIp.s_addr  = inet_addr(target);
	}
	int source_port = 4539;
	localAddress(source_ip);
	memset(buffer,0,4096);
	
	ip_header->ihl = 5;
	ip_header->version = 4;
	ip_header->tos = 0;
	ip_header->tot_len = sizeof(struct ip) + sizeof(struct tcphdr);
	ip_header->id = htons(54322);
	ip_header->frag_off = htons(16384);
	ip_header->ttl = 255;
	ip_header->protocol = IPPROTO_TCP;
	ip_header->check = 0;
	ip_header->saddr = inet_addr(source_ip);
	ip_header->daddr = destIp.s_addr;
	tcp_header->source = htons(source_port);
	tcp_header->dest = htons(port);
	tcp_header->seq = htonl(110);
	tcp_header->ack_seq = 0;
	tcp_header->doff= sizeof(struct tcphdr)/4;
	tcp_header->fin = 0;
	tcp_header->syn = 0;
	tcp_header->ack = 1;
	tcp_header->rst = 0;
	tcp_header->psh = 0;
	tcp_header->urg = 0;
	tcp_header->window = htons(14600);
	tcp_header->check = 0;
	tcp_header->urg_ptr = 0;

	
    int one = 1;
    const int *val = &one;
	printf("\n sending packet....\n"); 
    if (setsockopt (socket_id, IPPROTO_IP, IP_HDRINCL, val, sizeof (one)) < 0)
    {
        printf ("Error setting IP_HDRINCL. Error number : %d . Error message : %s \n" , errno , strerror(errno));
        exit(0);
    }
	dest.sin_family = AF_INET;
	dest.sin_addr.s_addr = destIp.s_addr;
	psh.source_Address = inet_addr( source_ip );
        psh.d_Address = dest.sin_addr.s_addr;
        psh.placeholder = 0;
        psh.protocol = IPPROTO_TCP;
        psh.tcp_length = htons( sizeof(struct tcphdr) );
        memcpy(&psh.tcp , tcp_header , sizeof (struct tcphdr));
         
        tcp_header->check = find_Checksum( (unsigned short*) &psh , sizeof (struct p_header)); 	
	int result = sendto (socket_id, buffer , sizeof(struct iphdr) + sizeof(struct tcphdr) , 0 , (struct sockaddr *) &dest, sizeof (dest));
	
	if ( result< 0)
        {
            printf ("Error sending ACK  packet Error message : %s \n" , strerror(errno));
            exit(0);
        }

	int saddr_size , data_size;
	struct sockaddr saddr;
     
	unsigned char *data = (unsigned char *)malloc(65536); 
	fflush(stdout);
	printf("\n receving packet ACK reply ......\n");
	saddr_size = sizeof saddr;
	data_size = recvfrom(socket_id , data , 65536 , 0 , &saddr , &saddr_size);
         
        if(data_size <0 )
        {
            printf("Recvfrom error \n");
            fflush(stdout);
            return 1;
        }
	
	analysePacket(data,data_size);
	

	return 0;
}

int localAddress ( char * buffer)
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

unsigned short find_Checksum(unsigned short *ptr,int nbytes) 
{
    register long sum;
    unsigned short oddbyte;
    register short res;
 
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
    res=(short)~sum;
    return(res);
}
 

void analysePacket(unsigned char* buffer, int size)
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
	  printf("Succedded\n");
	}
	else{
	  printf("Failed\n");
	}
    }
}
