import { Injectable } from '@angular/core';
import Web3 from "web3";

@Injectable({
  providedIn: 'root'
})
export class Web3Service {

  web3!: Web3;

  constructor() {
    
    // if in browser and metamask is running
    if (typeof (window as any).ethereum !== 'undefined' && typeof (window as any).ethereum !== "undefined") {
      (window as any).ethereum.request({ method: 'eth_requestAccounts' });
      this.web3 = new Web3((window as any).ethereum);
    } else {
      console.log('No ethereum provider detected. Please install MetaMask or another Web3 provider.');
    }
  }

}
