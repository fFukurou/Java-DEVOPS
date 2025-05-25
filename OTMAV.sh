#!/bin/bash

VM_NAME="vm_otmav"
RESOURCE_GROUP="rg-otmav"
USERNAME="rm556862"
PASSWORD="Erick@010506"
LOCATION="eastus"
IMAGE="Canonical:0001-com-ubuntu-server-focal:20_04-lts:latest"
VM_SIZE="Standard_B2s"
REPO_URL="https://github.com/Os-Tres-Motoqueiros-do-Apocalipse-Verde/Java-Advanced" # <-- Altere para seu repositório real

az group create --name $RESOURCE_GROUP --location $LOCATION

az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image $IMAGE \
  --admin-username $USERNAME \
  --admin-password $PASSWORD \
  --size $VM_SIZE \
  --public-ip-sku Basic \
  --output json

VM_IP=$(az vm show -d -g $RESOURCE_GROUP -n $VM_NAME --query publicIps -o tsv)

az vm open-port --resource-group $RESOURCE_GROUP --name $VM_NAME --port 8080

az vm run-command invoke \
  --command-id RunShellScript \
  --name $VM_NAME \
  --resource-group $RESOURCE_GROUP \
  --scripts "
    sudo apt update &&
    sudo apt install -y git &&
    curl -fsSL https://get.docker.com | sudo bash &&
    git clone $REPO_URL projeto &&
    cd projeto &&
    docker build -t otmav-api . &&
    docker run -d -p 8080:8080 otmav-api
  "