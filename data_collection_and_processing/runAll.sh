./get_data.sh init_data_urls_10000.txt
echo "Successfully gathered data from urls..."
./get_rel_data.sh
echo "Successfully collected relevant data from total data..."
python filterData.py
echo "Successfully filtered data"
python combineCVSData.py
echo "Succesfully combined data"

