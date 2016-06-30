shopt -s nullglob
FILES=raw_data_files/*.fits
for f in $FILES
do
  python getRelData.py $f
  if [ $? -eq 0 ]; then
    echo -ne "."
  else
    echo FAILED to process $f
  fi
done 
echo ""

