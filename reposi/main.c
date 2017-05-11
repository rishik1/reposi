#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<error.h>
#include<netdb.h>
#include<netinet/tcp.h>
#include<netinet/ip.h>


struct p_header{

unsigned int source_head;
unsigned int dest_head;
unsigned char protocol;
unsigned char placeholder;
unsigned short tcplength;
struct tcphdr tcp;

};

strcut init_addr dest_ip;



int get_local_ip ( char * buffer)
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
 
    const char *p = inet_ntop(AF_INET, &name.sin_addr, buffer, 100);
 
    close(sock);
}

unsigned short csum(unsigned short *ptr,int nbytes) 
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

void process_packet(unsigned char* buffer, int size)
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

        if(tcp->rst == 1 && source.sin_addr.s_addr == dest_ip.s_addr )
        {
            printf("Port %d succeded \n" , ntohs(tcph->source));
            fflush(stdout);
        }
	else{
	   printf("FAILED");
	}
    }
}

int main(int argc,char *argv[])
{

    int sokt = socket (AF_INET, SOCK_RAW , IPPROTO_TCP);
    if(sokt < 0)
    {
        printf ("Error creating socket. Error number : %d . Error message : %s \n" , errno , strerror(errno));
        exit(0);
    }
     else
    {
        printf("Socket created.\n");
    }

    char datagm[4096];    

    //IP header
    struct iphdr *ipheadr = (struct iphdr *) datagm;

    //TCP header
    struct tcphdr *tcpheadr = (struct tcphdr *) (datagm + sizeof (struct ip));

    struct sockaddr_in  destn;
    struct p_header psesuohdr;

    char *target_ip = argv[1];

    if(argc < 3)
    {
        printf("Please enter arguments correctly \n");
        exit(1);
    }

    if( inet_addr( target ) != -1)
    {
        dest_ip.s_addr = inet_addr( target_ip );
    }
    else
    {
        char *ip = hostname_to_ip(target);
        if(ip != NULL)
        {
            printf("%s resolved to %s \n" , target , ip);
            dest_ip.s_addr = inet_addr( hostname_to_ip(target) );
        }
        else
        {
            printf("Unable to resolve hostname : %s" , target);
            exit(1);
        }
    }

int source_port = 43591;
    char source_ip[20];
    get_local_ip( source_ip );
   printf("Local source IP is %s \n" , source_ip);

    memset (datagram, 0, 4096); /* zero out the buffer */
  //Fill in the IP Header
    ipheader->ihl = 5;
    ipheader->version = 4;
    ipheader->tos = 0;
    ipheader->tot_len = sizeof (struct ip) + sizeof (struct tcphdr);
    ipheader->id = htons (54321); //Id of this packet
    ipheader->frag_off = htons(16384);
    ipheader->ttl = 64;
    ipheader->protocol = IPPROTO_TCP;
    ipheader->check = 0;      //Set to 0 before calculating checksum
    ipheader->saddr = inet_addr ( source_ip );    //Spoof the source ip address
    ipheader->daddr = dest_ip.s_addr;

    iph->check = csum ((unsigned short *) datagram, iph->tot_len >> 1);

     //TCP Header
    tcpheader->source = htons ( source_port );
    tcpheader->dest = htons (80);
    tcpheader->seq = htonl(1105024978);
    tcpheader->ack_seq = 0;
    tcpheader->doff = sizeof(struct tcphdr) / 4;      //Size of tcp header
    tcpheader->fin=0;
    tcpheader->syn=0;
    tcpheader->rst=0;
    tcpheader->psh=0;
    tcpheader->ack=1;
    tcpheader->urg=0;
    tcpheader->window = htons ( 14600 );  // maximum allowed window size
    tcpheader->check = 0; //if you set a checksum to zero, your kernel's IP stack should fill in the correct checksum during transmission
    tcpheader->urg_ptr = 0;



retrun 0;

}


