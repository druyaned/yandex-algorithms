package com.github.druyaned.yandexalgorithms.train5.l3setmap.p10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.Consumer;

public class P2PUpdate {
    
    private static final char[] BUFFER = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int deviceCount = readInt(reader);
        int partCount = readInt(reader);
        TimeslotManager timeslotManager = new TimeslotManager(deviceCount, partCount);
        timeslotManager.run();
        timeslotManager.writeTimeslotIds(writer);
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(BUFFER, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        BUFFER[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            BUFFER[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}

class Device {
    
    public static final Comparator<Device> byId = (d1, d2) -> d1.id - d2.id;
    public static final Comparator<Device> choice = (d1, d2) ->
            d2.missingPartsSize() == d1.missingPartsSize() ? d1.id - d2.id :
            d2.missingPartsSize() - d1.missingPartsSize();
    
    public final int id;
    private final TreeSet<Part> missingParts;
    private final int[] sendCounts;
    
    public Device(int id, int deviceCount, Part[] parts) {
        this.id = id;
        missingParts = new TreeSet(Part.byId);
        missingParts.addAll(Arrays.asList(parts));
        sendCounts = new int[deviceCount + 1];
    }
    
    public int missingPartsSize() {
        return missingParts.size();
    }
    
    public int sendCountTo(Device device) {
        return sendCounts[device.id];
    }
    
    public void addPart(Part part) {
        missingParts.remove(part);
    }
    
    public void addSendCountTo(Device device) {
        sendCounts[device.id]++;
    }
    
    public void forEachMissingPart(Consumer<Part> consumer) {
        missingParts.forEach(consumer);
    }
    
}

class Part {
    
    public static final Comparator<Part> byId = (p1, p2) -> p1.id - p2.id;
    public static final Comparator<Part> choice = (p1, p2) ->
            p1.ownerCount() == p2.ownerCount() ? p1.id - p2.id :
            p1.ownerCount() - p2.ownerCount();
    
    public final int id;
    private final TreeSet<Device> owners;
    
    public Part(int id) {
        this.id = id;
        owners = new TreeSet(Device.byId);
    }
    
    public int ownerCount() {
        return owners.size();
    }
    
    public void addOwner(Device owner) {
        owners.add(owner);
    }
    
    public void forEachOwner(Consumer<Device> consumer) {
        owners.forEach(consumer);
    }
    
}

class Request {
    
    public final Device receiver;
    public final Part send;
    
    public Request(Device receiver, Part send) {
        this.receiver = receiver;
        this.send = send;
    }
    
}

class RequestComparator implements Comparator<Request> {
    
    private final Device sender;
    
    public RequestComparator(Device sender) {
        this.sender = sender;
    }
    
    @Override public int compare(Request r1, Request r2) {
        return r2.receiver.sendCountTo(sender) == r1.receiver.sendCountTo(sender) ?
                r2.receiver.missingPartsSize() == r1.receiver.missingPartsSize() ?
                    r1.receiver.id - r2.receiver.id :
                r2.receiver.missingPartsSize() - r1.receiver.missingPartsSize() :
            r2.receiver.sendCountTo(sender) - r1.receiver.sendCountTo(sender);
    }
    
}

class TimeslotManager implements Runnable {
    
    private final Device[] devices;
    private final Part[] parts;
    private final Part[] receiverToMissingPart;
    private final Device[] partToOwner;
    private final Request[] senderToRequest;
    private final RequestComparator[] reqComps;
    private int receiverCount;
    private final int[] ids;
    private int currentId;
    
    public TimeslotManager(int deviceCount, int partCount) {
        devices = new Device[deviceCount];
        parts = new Part[partCount];
        for (int i = 0; i < partCount; i++) {
            parts[i] = new Part(i);
        }
        devices[0] = new Device(0, deviceCount, parts);
        for (int i = 0; i < partCount; i++) {
            devices[0].addPart(parts[i]);
            parts[i].addOwner(devices[0]);
        }
        for (int i = 1; i < deviceCount; i++) {
            devices[i] = new Device(i, deviceCount, parts);
        }
        receiverToMissingPart = new Part[deviceCount];
        partToOwner = new Device[partCount];
        senderToRequest = new Request[deviceCount];
        reqComps = new RequestComparator[deviceCount];
        for (int i = 0; i < deviceCount; i++) {
            reqComps[i] = new RequestComparator(devices[i]);
        }
        ids = new int[deviceCount];
    }
    
    @Override public void run() {
        prepare();
        while (receiverCount > 0) {
            for (int i = 0; i < devices.length; i++) {
                if (receiverToMissingPart[i] != null) {
                    Device receiver = devices[i];
                    Part send = receiverToMissingPart[i];
                    Device sender = partToOwner[send.id];
                    if (senderToRequest[sender.id] == null) {
                        senderToRequest[sender.id] = new Request(receiver, send);
                    } else {
                        Request newRequest = new Request(receiver, send);
                        int comparison = reqComps[sender.id]
                                .compare(senderToRequest[sender.id], newRequest);
                        if (comparison > 0) {
                            senderToRequest[sender.id] = newRequest;
                        }
                    }
                }
            }
            for (int i = 0; i < devices.length; i++) {
                if (senderToRequest[i] != null) {
                    Device sender = devices[i];
                    Device receiver = senderToRequest[i].receiver;
                    Part send = senderToRequest[i].send;
                    receiver.addPart(send);
                    sender.addSendCountTo(receiver);
                    send.addOwner(receiver);
                    if (receiver.missingPartsSize() == 0) {
                        ids[receiver.id] = currentId;
                    }
                }
            }
            prepare();
        }
    }
    
    public void writeTimeslotIds(BufferedWriter writer) throws IOException {
        writer.write(Integer.toString(ids[1]));
        for (int i = 2; i < ids.length; i++) {
            writer.write(" " + ids[i]);
        }
        writer.write('\n');
    }
    
    private void prepare() {
        receiverCount = 0;
        for (int i = 0; i < devices.length; i++) {
            Part[] send = { null };
            devices[i].forEachMissingPart(missingPart -> {
                if (send[0] == null) {
                    send[0] = missingPart;
                } else if (Part.choice.compare(send[0], missingPart) > 0) {
                    send[0] = missingPart;
                }
            });
            if ((receiverToMissingPart[i] = send[0]) != null) {
                receiverCount++;
            }
            senderToRequest[i] = null;
        }
        for (int i = 0; i < parts.length; i++) {
            Device[] sender = { null };
            parts[i].forEachOwner(owner -> {
                if (sender[0] == null) {
                    sender[0] = owner;
                } else if (Device.choice.compare(sender[0], owner) > 0) {
                    sender[0] = owner;
                }
            });
            partToOwner[i] = sender[0];
        }
        currentId++;
    }
    
}
/*
Файл обновления разбивается на k одинаковых
по размеру частей, занумерованных от 1 до k.

Передача части обновления происходит во время таймслотов.
Каждый таймслот занимает одну минуту.
За один таймслот каждое устройство может
получить и передать ровно одну часть обновления.
То есть устройство во время таймслота может
получать новую часть обновления и передавать
уже имеющуюуся у него к началу таймслота
часть обновления, или совершать только одно
из этих действий, или вообще не осуществлять
прием или передачу. После приема части обновления
устройство может передавать эту часть обновления
другим устройствам в следующих таймслотах.

Перед каждым таймслотом для каждой части обновления определяется,
на скольких устройствах сети скачана эта часть.
Каждое устройство выбирает отсутствующую на нем часть обновления,
которая встречается в сети реже всего.
Если таких частей несколько, то выбирается отсутствующая на устройстве
часть обновления с наименьшим номером.

После этого устройство делает запрос выбранной части обновления
у одного из устройств, на котором такая часть обновления уже скачана.
Если таких устройств несколько — выбирается устройство,
на котором скачано наименьшее количество частей обновления.
Если и таких устройств оказалось несколько — выбирается устройство
с минимальным номером.

После того, как все запросы отправлены, каждое устройство выбирает,
чей запрос удовлетворить.
Устройство A удовлетворяет тот запрос, который поступил от
наиболее ценного для A устройства.

Ценность устройства B для устройства A определяется как количество
частей обновления, ранее полученных устройством A от устройства B.
Если на устройство A пришло несколько запросов от одинаково ценных устройств,
то удовлетворяется запрос того устройства,
на котором меньше всего скачанных частей обновления.
Если и таких запросов несколько, то среди них выбирается устройство
с наименьшим номером.

Далее начинается новый таймслот. Устройства, чьи запросы удовлетворены,
скачивают запрошенную часть обновления, а остальные не скачивают ничего.

Для каждого устройства определите, сколько таймслотов понадобится
для скачивания всех частей обновления.

Формат ввода {
    Вводится два числа n и k (2 ≤ n ≤ 100, 1 ≤ k ≤ 200).
}

Формат вывода {
    Выведите n-1 число — количество таймслотов,
    необходимых для скачивания обновления на устройства с номерами от 2 до n.
}
*/