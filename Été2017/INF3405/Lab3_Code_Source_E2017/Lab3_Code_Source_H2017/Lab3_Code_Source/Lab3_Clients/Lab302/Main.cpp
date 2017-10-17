#undef UNICODE

#include <winsock2.h>
#include <ws2tcpip.h>
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <string>

// Link avec ws2_32.lib
#pragma comment(lib, "ws2_32.lib")

int __cdecl main(int argc, char **argv)
{
    WSADATA wsaData;
    SOCKET leSocket;// = INVALID_SOCKET;
    struct addrinfo *result = NULL,
                    *ptr = NULL,
                    hints;
    char motEnvoye[10];
	char motRecu[10];
    int iResult;

	//--------------------------------------------
    // InitialisATION de Winsock
    iResult = WSAStartup(MAKEWORD(2,2), &wsaData);
    if (iResult != 0) {
        printf("Erreur de WSAStartup: %d\n", iResult);
        return 1;
    }
	// On va creer le socket pour communiquer avec le serveur
	leSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (leSocket == INVALID_SOCKET) {
        printf("Erreur de socket(): %ld\n\n", WSAGetLastError());
        freeaddrinfo(result);
        WSACleanup();
		printf("Appuyez une touche pour finir\n");
		getchar();
        return 1;
	}
	//--------------------------------------------
	// On va chercher l'adresse du serveur en utilisant la fonction getaddrinfo.
    ZeroMemory( &hints, sizeof(hints) );
    hints.ai_family = AF_INET;        // Famille d'adresses
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_protocol = IPPROTO_TCP;  // Protocole utilisé par le serveur

	// On indique le nom et le port du serveur auquel on veut se connecter
	//char *host = "L4708-XX";
	//char *host = "L4708-XX.lerb.polymtl.ca";
	//char *host = "add_IP locale";
	bool srvrOK = false;
	std::string srvr;
	while (!srvrOK)
	{
		std::cout << "Please, enter the full address of the server: ";
		std::getline(std::cin, srvr);
		std::string delimiter = ".";
		std::string part1 = srvr.substr(0, srvr.find(delimiter));
		std::string adressWithoutExaminedPart = srvr.substr((int) part1.length() + 1, (int) srvr.length() - (int) part1.length());
		int index = srvr.find(part1);
		index += 1 + (int) part1.length();
		std::string part2 = srvr.substr(index, adressWithoutExaminedPart.find(delimiter));
		adressWithoutExaminedPart = adressWithoutExaminedPart.substr((int) part2.length() + 1, (int) adressWithoutExaminedPart.length() - (int) part2.length());
		index += 1 + part2.length();
		std::string part3 = srvr.substr(index, adressWithoutExaminedPart.find(delimiter));
		adressWithoutExaminedPart = adressWithoutExaminedPart.substr((int) part3.length() + 1, (int) adressWithoutExaminedPart.length() - (int) part3.length());
		index += 1 + part3.length();
		std::string part4 = srvr.substr(index, adressWithoutExaminedPart.find(delimiter));
		if(part1 == "132" && part2 == "207" && part3 == "29")
		{
			int part4Int = std::stoi(part4);
			if (part4Int < 130 && part4Int > 100)
			{
				srvrOK = true;
			}
		}
	}
	
	char *host = strdup(srvr.c_str());
	char *port = "5050";

	// getaddrinfo obtient l'adresse IP du host donné
    iResult = getaddrinfo(host, port, &hints, &result);
    if ( iResult != 0 ) {
        printf("Erreur de getaddrinfo: %d\n", iResult);
        WSACleanup();
        return 1;
    }
	//---------------------------------------------------------------------		
	//On parcours les adresses retournees jusqu'a trouver la premiere adresse IPV4
	while((result != NULL) &&(result->ai_family!=AF_INET))   
			 result = result->ai_next; 

//	if ((result != NULL) &&(result->ai_family==AF_INET)) result = result->ai_next;  
	
	//-----------------------------------------
	if (((result == NULL) ||(result->ai_family!=AF_INET))) {
		freeaddrinfo(result);
		printf("Impossible de recuperer la bonne adresse\n\n");
        WSACleanup();
		printf("Appuyez une touche pour finir\n");
		getchar();
        return 1;
	}

	sockaddr_in *adresse;
	adresse=(struct sockaddr_in *) result->ai_addr;
	//----------------------------------------------------
	printf("Adresse trouvee pour le serveur %s : %s\n\n", host,inet_ntoa(adresse->sin_addr));
	printf("Tentative de connexion au serveur %s avec le port %s\n\n", inet_ntoa(adresse->sin_addr),port);
	
	// On va se connecter au serveur en utilisant l'adresse qui se trouve dans
	// la variable result.
	iResult = connect(leSocket, result->ai_addr, (int)(result->ai_addrlen));
	if (iResult == SOCKET_ERROR) {
        printf("Impossible de se connecter au serveur %s sur le port %s\n\n", inet_ntoa(adresse->sin_addr),port);
        freeaddrinfo(result);
        WSACleanup();
		printf("Appuyez une touche pour finir\n");
		getchar();
        return 1;
	}

	printf("Connecte au serveur %s:%s\n\n", host, port);
    freeaddrinfo(result);

	//----------------------------
	// Demander à l'usager un mot a envoyer au serveur
	printf("Saisir un mot de 7 lettres pour envoyer au serveur: ");
	gets_s(motEnvoye);

	//-----------------------------
    // Envoyer le mot au serveur
    iResult = send(leSocket, motEnvoye, 7, 0 );
    if (iResult == SOCKET_ERROR) {
        printf("Erreur du send: %d\n", WSAGetLastError());
        closesocket(leSocket);
        WSACleanup();
		printf("Appuyez une touche pour finir\n");
	getchar();

        return 1;
    }

    printf("Nombre d'octets envoyes : %ld\n", iResult);

	//------------------------------
	// Maintenant, on va recevoir l' information envoyée par le serveur
	iResult = recv(leSocket, motRecu, 7, 0);
	if (iResult > 0) {
		printf("Nombre d'octets recus: %d\n", iResult);
		motRecu[iResult] = '\0';
		printf("Le mot recu est %*s\n", iResult, motRecu);
	}
    else {
        printf("Erreur de reception : %d\n", WSAGetLastError());
    }

    // cleanup
    closesocket(leSocket);
    WSACleanup();

	printf("Appuyez une touche pour finir\n");
	getchar();
    return 0;
}