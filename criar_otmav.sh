#!/bin/bash

# Variáveis de configuração
VM_NAME="vm_otmav"
RESOURCE_GROUP="rg-otmav"
USERNAME="rm556862"
PASSWORD="Erick@010506"
LOCATION="eastus"
IMAGE="Canonical:0001-com-ubuntu-server-focal:20_04-lts:latest"
VM_SIZE="Standard_B2s"
REPO_URL="https://github.com/Os-Tres-Motoqueiros-do-Apocalipse-Verde/Java-Advanced"

# Criar grupo de recursos
az group create --name $RESOURCE_GROUP --location $LOCATION

# Criar VM
az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image $IMAGE \
  --admin-username $USERNAME \
  --admin-password $PASSWORD \
  --size $VM_SIZE \
  --public-ip-sku Basic \
  --output json

# Obter o IP público da VM (opcional, mas útil para debug)
VM_IP=$(az vm show -d -g $RESOURCE_GROUP -n $VM_NAME --query publicIps -o tsv)
echo "IP da VM: $VM_IP"

# Garantir que a porta 8080 esteja liberada no NSG associado
NSG_NAME=$(az network nic show \
  --resource-group $RESOURCE_GROUP \
  --name ${VM_NAME}VMNic \
  --query 'networkSecurityGroup.id' -o tsv | awk -F/ '{print $NF}')

az network nsg rule create \
  --resource-group $RESOURCE_GROUP \
  --nsg-name $NSG_NAME \
  --name Allow-8080 \
  --priority 1001 \
  --destination-port-ranges 8080 \
  --protocol Tcp \
  --access Allow \
  --direction Inbound \
  --source-address-prefixes '*' \
  --destination-address-prefixes '*'

# Rodar comandos de setup na VM
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

echo "Acesse a aplicação em: http://$VM_IP:8080/swagger-ui/index.html"
